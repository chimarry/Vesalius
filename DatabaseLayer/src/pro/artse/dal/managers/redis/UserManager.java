package pro.artse.dal.managers.redis;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.ILocationManager;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.UserDTO;
import pro.artse.dal.models.UserLocationDTO;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Manipulates user's information in Redis database.
 * 
 * @author Marija
 *
 */
public class UserManager implements IUserManager {

	private static final String TOKENS_KEY = "tokens";
	private ILocationManager locationManager = ManagerFactory.getLocationManager();

	@Override
	public DBResultMessage<Boolean> add(UserDTO user) {
		if (!isValid(user))
			return new DBResultMessage<Boolean>(DbStatus.INVALID_DATA);
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			// Save as hash
			String key = user.getKeyUserInfoDTO().getToken();
			if (jedis.exists(key))
				return new DBResultMessage<Boolean>(DbStatus.EXISTS);
			jedis.hmset(key, user.mapAttributes());

			// Add token to index
			jedis.sadd(TOKENS_KEY, user.getKeyUserInfoDTO().getToken());
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return ErrorHandler.handle(ex);
		} catch (Exception e) {
			return ErrorHandler.handle(e);
		}
	}

	@Override
	public DBResultMessage<Boolean> blockUser(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			if (!jedis.exists(token) || jedis.hget(token, "isBlocked").equals("1"))
				return new DBResultMessage<Boolean>(DbStatus.NOT_FOUND);
			jedis.hset(token, "isBlocked", "1");
			// Remove from index
			jedis.srem(TOKENS_KEY, token);
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}

	@Override
	public DBResultMessage<KeyUserInfoDTO> search(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			Map<String, String> attributes = jedis.hgetAll(token);
			if (attributes.isEmpty())
				return new DBResultMessage<KeyUserInfoDTO>(DbStatus.NOT_FOUND);
			KeyUserInfoDTO userInfoDTO = new KeyUserInfoDTO(token);
			userInfoDTO.setCovidStatus(Integer.parseInt(attributes.get("covidStatus")));
			userInfoDTO.setIsBlocked(Integer.parseInt(attributes.get("isBlocked")));
			return new DBResultMessage<KeyUserInfoDTO>(userInfoDTO, DbStatus.SUCCESS);
		}
	}

	@Override
	public DBResultMessage<List<KeyUserInfoDTO>> getAllAllowedInformation() {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			List<KeyUserInfoDTO> basicUserInfos = jedis.smembers(TOKENS_KEY).stream().map(x -> {
				KeyUserInfoDTO user = new KeyUserInfoDTO(x);
				user.setCovidStatus(Integer.parseInt(jedis.hget(x, "covidStatus")));
				return user;
			}).collect(Collectors.toCollection(ArrayList<KeyUserInfoDTO>::new));
			return new DBResultMessage<List<KeyUserInfoDTO>>(basicUserInfos, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}

	@Override
	public DBResultMessage<Boolean> isValidToken(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String result = jedis.hget(token, "isBlocked");
			if (result == null || result.equals("1"))
				return new DBResultMessage<Boolean>(DbStatus.NOT_FOUND);
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}

	/**
	 * Checks if provided data about the user is valid.
	 * 
	 * @param user UserDTO's information to check
	 * @return True if is valid, false if not.
	 */
	private boolean isValid(UserDTO user) {
		return user != null && user.getKeyUserInfoDTO() != null && !user.getKeyUserInfoDTO().getToken().equals("");
	}

	@Override
	public DBResultMessage<List<UserLocationDTO>> markUsersAsPotentiallyInfected(String token,
			LocationDTO locationOfInfection, int distanceInMeters, int timeInterval) {
		List<UserLocationDTO> potentiallyInfectedUsers;

		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			potentiallyInfectedUsers = jedis.keys("*==#locations").stream()
					// Get only healthy users
					.filter(x -> jedis.hget(x.split("#")[0], "covidStatus").equals("0")
							&& !x.split("#")[0].equals(token))
					// Check if that user could be infected and save that location
					.map(x -> {
						LocationDTO location = locationManager.isInRange(x, locationOfInfection, distanceInMeters,
								timeInterval);
						return location == null ? null : new UserLocationDTO(location, x.split("#")[0]);
					}).filter(x -> x != null).collect(Collectors.toCollection(ArrayList<UserLocationDTO>::new));
			// Mark them as potentially infected
			potentiallyInfectedUsers.forEach(x -> jedis.hset(x.getToken(), "covidStatus", "1"));

			return new DBResultMessage<List<UserLocationDTO>>(potentiallyInfectedUsers, DbStatus.SUCCESS);
		} catch (DateTimeParseException e) {
			return ErrorHandler.handle(e);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	@Override
	public DBResultMessage<Boolean> changeCovidStatus(String token, int covidStatus) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			if (!jedis.exists(token))
				return new DBResultMessage<Boolean>(DbStatus.NOT_FOUND);
			long success = jedis.hset(token, "covidStatus", String.valueOf(covidStatus));
			if (success == 0)
				return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
			else
				return new DBResultMessage<Boolean>(false, DbStatus.SERVER_ERROR,
						"Could not change covid status of an user.");
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}
}
