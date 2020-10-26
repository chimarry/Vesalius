package pro.artse.dal.managers.redis;

import java.util.List;

import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.BasicUserInfo;
import pro.artse.dal.models.User;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Manipulates user's information in Redis database.
 * 
 * @author Marija
 *
 */
public class UserManager implements IUserManager {

	@Override
	public boolean add(User user) {
		if (!isValid(user))
			return false;
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String key = user.getBasicUserInfo().getToken();
			if (jedis.exists(key) && jedis.hget(key, "isDeactivated").equals("0"))
				return false;
			jedis.hmset(key, user.mapAttributes());
			return true;
		}
	}

	@Override
	public boolean deactivate(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			if (jedis.exists(token) && jedis.hget(token, "isDeactivated").equals("1"))
				return false;
			jedis.hset(token, "isDeactivated", "1");
			return true;
		}
	}

	@Override
	public List<BasicUserInfo> GetAllAllowedInformation() {
		return null;
	}

	@Override
	public boolean isValidToken(String token) {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			String result = jedis.hget(token, "isDeactivated");
			return !(result == null || result.equals("1"));
		}
	}

	/**
	 * Checks if provided data about the user is valid.
	 * 
	 * @param user User's information to check
	 * @return True if is valid, false if not.
	 */
	private boolean isValid(User user) {
		if (user != null && user.getBasicUserInfo() != null && !user.getBasicUserInfo().getToken().equals(""))
			return true;
		return false;
	}
}
