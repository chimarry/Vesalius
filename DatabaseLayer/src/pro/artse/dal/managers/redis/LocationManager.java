package pro.artse.dal.managers.redis;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.ILocationManager;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.util.DateTimeUtil;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class LocationManager implements ILocationManager {

	public static final String LOCATIONS_SUFFIX = "locations";
	public static final String SEPARATOR = "#";
	public static final String KEY_FORMAT = "%s" + SEPARATOR + "%s";
	public static final String DATETIME_FORMAT = "%s" + SEPARATOR + "%s";

	@Override
	public DBResultMessage<Boolean> saveLocation(String token, LocationDTO locationData) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String rememberTime = String.format(DATETIME_FORMAT, locationData.getSince(), locationData.getUntil());
			String key = String.format(KEY_FORMAT, token, LOCATIONS_SUFFIX);
			long status = jedis.geoadd(key, locationData.getLongitude(), locationData.getLatitude(), rememberTime);
			if (status == RedisConnector.ERROR)
				return new DBResultMessage<Boolean>(DbStatus.UNKNOWN_ERROR);
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (DateTimeParseException e) {
			return ErrorHandler.handle(e);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	@Override
	public DBResultMessage<List<LocationDTO>> getLocations(String token, int timeIntervalInDays) {
		ArrayList<LocationDTO> locations = new ArrayList<>();
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = String.format(KEY_FORMAT, token, LOCATIONS_SUFFIX);
			// Get names of locations of specific user
			Set<String> locationMembers = jedis.zrange(key, RedisConnector.START, RedisConnector.END);
			// Filter locations based on time
			String[] filteredLocations = locationMembers.stream().filter(x -> {
				String[] dates = x.split(SEPARATOR);
				return DateTimeUtil.isInRange(timeIntervalInDays, LocalDateTime.parse(dates[0]),
						LocalDateTime.parse(dates[1]));
			}).toArray(size -> new String[size]);
			// Get coordinates of filtered locations
			GeoCoordinate[] coordinates = jedis.geopos(key, filteredLocations).stream()
					.toArray(size -> new GeoCoordinate[size]);
			for (int i = 0; i < filteredLocations.length; ++i) {
				GeoCoordinate coordinate = coordinates[i];
				LocationDTO location = new LocationDTO();
				location.parseDates(filteredLocations[i], SEPARATOR);
				location.setLongitude(coordinate.getLongitude());
				location.setLatitude(coordinate.getLatitude());
				locations.add(location);
			}
			return new DBResultMessage<List<LocationDTO>>(locations, DbStatus.SUCCESS);
		} catch (DateTimeParseException e) {
			return ErrorHandler.handle(e);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	@Override
	public LocationDTO isInRange(String locationToken, LocationDTO location, int distanceInMeters, int timeInterval) {
		LocationDTO potentiallyInfectedOnLocation = null;
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			Optional<GeoRadiusResponse> hasBeenInContact = jedis.georadius(locationToken, location.getLongitude(),
					location.getLatitude(), distanceInMeters, GeoUnit.M).stream().filter(x -> {
						String member = x.getMemberByString();
						String[] dates = member.split(SEPARATOR);
						return DateTimeUtil.areOverlapped(location.getSince(), location.getUntil(),
								LocalDateTime.parse(dates[0]), LocalDateTime.parse(dates[1]), timeInterval);
					}).findAny();
			if (hasBeenInContact.isPresent()) {
				GeoRadiusResponse response = hasBeenInContact.get();
				String[] dates = response.getMemberByString().split(SEPARATOR);
				potentiallyInfectedOnLocation = new LocationDTO(response.getCoordinate().getLongitude(),
						response.getCoordinate().getLatitude(), LocalDateTime.parse(dates[0]),
						LocalDateTime.parse(dates[1]));
			}
		}
		return potentiallyInfectedOnLocation;
	}
}
