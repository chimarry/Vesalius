package pro.artse.dal.managers.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.org.apache.bcel.internal.Const;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.errorhandling.ErrorHandler;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.UserDTO;
import pro.artse.dal.models.ActivityLogDTO.ActivityDTO;
import pro.artse.dal.util.RedisConnector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Manipulates user's information in Redis database.
 * 
 * @author Marija
 *
 */
public class UserManager implements IUserManager {

	private static final String TOKEN_SET_NAME = "tokens";

	@Override
	public DBResultMessage<Boolean> add(UserDTO user) {
		// TODO: Add mechanism for deactivating user
		if (!isValid(user))
			return new DBResultMessage<Boolean>(DbStatus.INVALID_DATA);
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			// Save as hash
			String key = user.getKeyUserInfoDTO().getToken();
			if (jedis.exists(key))// && jedis.hget(key, "isDeactivated").equals("0"))
				return new DBResultMessage<Boolean>(DbStatus.EXISTS);
			jedis.hmset(key, user.mapAttributes());

			// Add to set of tokens
			jedis.sadd(TOKEN_SET_NAME, user.getKeyUserInfoDTO().toString());
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
	public DBResultMessage<List<KeyUserInfoDTO>> getAllAllowedInformation() {
		try (Jedis jedis = RedisConnector.createConnection().getResource()) {
			List<KeyUserInfoDTO> basicUserInfos = jedis.smembers(TOKEN_SET_NAME).stream()
					.map(x -> new KeyUserInfoDTO(x)).collect(Collectors.toCollection(ArrayList<KeyUserInfoDTO>::new));
			return new DBResultMessage<List<KeyUserInfoDTO>>(basicUserInfos, DbStatus.SUCCESS);
		} catch (JedisConnectionException ex) {
			return ErrorHandler.handle(ex);
		}
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
	 * @param user UserDTO's information to check
	 * @return True if is valid, false if not.
	 */
	private boolean isValid(UserDTO user) {
		return user != null && user.getKeyUserInfoDTO() != null && !user.getKeyUserInfoDTO().getToken().equals("");
	}
}
