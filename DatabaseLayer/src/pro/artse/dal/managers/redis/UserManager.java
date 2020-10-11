package pro.artse.dal.managers.redis;

import java.util.List;

import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.BasicUserInfo;
import pro.artse.dal.models.User;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;

/**
 * Manipulates user's information in Redis database.
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
			jedis.hmset(key, user.mapAttributes());
		}
		return true;
	}

	@Override
	public boolean deactivate(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BasicUserInfo> GetAllAllowedInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/** Checks if provided data about the user is valid.
	 * @param user User's information to check
	 * @return True if is valid, false if not.
	 */
	private boolean isValid(User user) {
		if (user != null && user.getBasicUserInfo() != null && !user.getBasicUserInfo().getToken().equals(""))
			return true;
		return false;
	}
}
