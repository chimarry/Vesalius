package pro.artse.dal.managers.redis;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import pro.artse.dal.managers.IActivityLogManager;
import pro.artse.dal.models.*;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;

/**
 * Implements IActivityLogManager using Redis database.
 * 
 * @author Marija
 *
 */
public class ActivityLogManager implements IActivityLogManager {

	public static final String ACTIVITIES_SUFFIX = "activities";
	private static final int BEGIN = 0;
	private static final int END = -1;

	/**
	 * Adds user's activity in Redis database.
	 */
	@Override
	public boolean add(ActivityLogDTO userActivity) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = userActivity.getToken() + ACTIVITIES_SUFFIX;
			String value = userActivity.toString();
			jedis.set(key, value);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns list of user's activities.
	 */
	public List<ActivityDTO> getAll(String token) {
		List<ActivityDTO> activities = new ArrayList<>();
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = token + ACTIVITIES_SUFFIX;
			activities = jedis.lrange(key, BEGIN, END).stream().map(x -> new ActivityLogDTO.ActivityDTO(x))
					.collect(Collectors.toCollection(ArrayList<ActivityDTO>::new));
			;

			return activities;
		} catch (DateTimeParseException e) {
			return activities;
		}
	}
}
