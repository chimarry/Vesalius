package pro.artse.dal.managers.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.INotificationManager;
import pro.artse.dal.models.NotificationDTO;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class NotificationManager implements INotificationManager {

	public static final String NOTIFICATION_SUFFIX = "notifications";
	public static final String KEY_FORMAT = "%s#%s";

	@Override
	public DBResultMessage<Boolean> addNotification(NotificationDTO notification) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			// Save notification data
			String notificationKey = notification.getName();
			jedis.hset(notificationKey, notification.mapAttributes());

			// Add to index
			String indexKey = String.format(KEY_FORMAT, notification.getToken(), NOTIFICATION_SUFFIX);
			jedis.rpush(indexKey, notificationKey);

			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	@Override
	public DBResultMessage<List<NotificationDTO>> getNewerNotifications(String user) {
		ArrayList<NotificationDTO> notifications = new ArrayList<>();
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String indexKey = String.format(KEY_FORMAT, user, NOTIFICATION_SUFFIX);
			jedis.lrange(indexKey, RedisConnector.START, RedisConnector.END).stream().forEach(notificationKey -> {
				notifications.add(getNotification(jedis, notificationKey));
				jedis.del(notificationKey);
			});
			jedis.del(indexKey);
			return new DBResultMessage<List<NotificationDTO>>(notifications, DbStatus.SUCCESS);
		} catch (JedisConnectionException e) {
			return ErrorHandler.handle(e);
		}
	}

	private NotificationDTO getNotification(Jedis jedis, String key) {
		Map<String, String> values = jedis.hgetAll(key);
		return NotificationDTO.buildNotification(key, values);
	}
}