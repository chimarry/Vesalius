package pro.artse.dal.managers.redis;

import java.time.format.DateTimeParseException;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.ILocationManager;
import pro.artse.dal.models.LocationData;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class LocationManager implements ILocationManager {

	public static final String LOCATIONS_SUFFIX = "locations";
	public static final String KEY_FORMAT = "%s#%s";
	public static final String DATETIME_FORMAT = "%s#%s";

	@Override
	public DBResultMessage<Boolean> saveLocation(String token, LocationData locationData) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String rememberTime = String.format(DATETIME_FORMAT, locationData.getArrivedOn(), locationData.getLeftOn());
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
}
