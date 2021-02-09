package pro.artse.dal.managers.redis;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.IActivityLogManager;
import pro.artse.dal.models.*;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Implements IActivityLogManager using Redis database.
 * 
 * @author Marija
 *
 */
public class ActivityLogManager implements IActivityLogManager {

	public static final String ACTIVITIES_SUFFIX = "activities";

	/**
	 * Adds user's activity in Redis database.
	 */
	@Override
	public DBResultMessage<Boolean> add(ActivityLogDTO userActivity) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = userActivity.getToken() + ACTIVITIES_SUFFIX;
			String value = userActivity.toString();
			long status = jedis.rpush(key, value);
			if (status == RedisConnector.ERROR)
				return new DBResultMessage<Boolean>(DbStatus.UNKNOWN_ERROR);
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (DateTimeParseException e) {
			return ErrorHandler.handle(e);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	/**
	 * Returns list of user's activities.
	 */
	public DBResultMessage<List<ActivityDTO>> getAll(String token) {
		List<ActivityDTO> activities = new ArrayList<>();
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = token + ACTIVITIES_SUFFIX;
			activities = jedis.lrange(key, RedisConnector.START, RedisConnector.END).stream()
					.map(x -> new ActivityLogDTO.ActivityDTO(x))
					.collect(Collectors.toCollection(ArrayList<ActivityDTO>::new));
			;
			return new DBResultMessage<List<ActivityDTO>>(activities, DbStatus.SUCCESS);
		} catch (DateTimeParseException e) {
			return ErrorHandler.handle(e, activities);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e, activities);
		}
	}

	@Override
	public DBResultMessage<Boolean> deleteAll(String token) {
		String key = token + ACTIVITIES_SUFFIX;
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			if (jedis.del(key) == RedisConnector.SUCCESS)
				return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
			return new DBResultMessage<Boolean>(false, DbStatus.NOT_FOUND);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}
}
