package pro.artse.dal.managers.redis;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.BasicUserInfo;
import pro.artse.dal.models.User;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Manipulates user's information in Redis database.
 * 
 * @author Marija
 *
 */
public class UserManager implements IUserManager {

	@Override
	public DBResultMessage<Boolean> add(User user) {
		if (!isValid(user))
			return new DBResultMessage<Boolean>(DbStatus.INVALID_DATA);
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = user.getBasicUserInfo().getToken();
			if (jedis.exists(key) && jedis.hget(key, "isDeactivated").equals("0"))
				return new DBResultMessage<Boolean>(DbStatus.EXISTS);
			jedis.hmset(key, user.mapAttributes());
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}

	@Override
	public DBResultMessage<Boolean> deactivate(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			if (jedis.exists(token) && jedis.hget(token, "isDeactivated").equals("1"))
				return new DBResultMessage<Boolean>(DbStatus.NOT_FOUND);
			jedis.hset(token, "isDeactivated", "1");
			return new DBResultMessage<Boolean>(true, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
	}

	@Override
	public DBResultMessage<List<BasicUserInfo>> GetAllAllowedInformation() {
		return null;
	}

	@Override
	public DBResultMessage<Boolean> isValidToken(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String result = jedis.hget(token, "isDeactivated");
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
	 * @param user User's information to check
	 * @return True if is valid, false if not.
	 */
	private boolean isValid(User user) {
		return user != null && user.getBasicUserInfo() != null && !user.getBasicUserInfo().getToken().equals("");
	}
}
