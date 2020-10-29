package pro.artse.tokenserver.services;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.Gson;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.User;
import pro.artse.tokenserver.mappers.Mapper;
import pro.artse.tokenserver.models.Credentials;

/**
 * @author Marija Implements interface for managing tokens.
 */
public class TokenService implements ITokenService {

	/**
	 * Responsible for interaction with storage.
	 */
	private final IUserManager userManager = ManagerFactory.getUserManager();

	/**
	 * Generates unique token based on provided user's credentials, saves that token
	 * to database and returns it as base64 encoded string to the user.
	 * 
	 * @param credentials User credentials.
	 */
	@Override
	public String generateToken(String firstName, String lastName, String ubn) {
		Gson serializer = new Gson();
		serializer.serializeNulls();

		// Validate ubn
		if (ubn == null || ubn.isEmpty())
			return serializer.toJson(new ResultMessage<String>(OperationStatus.USER_ERROR));

		// Generate token
		byte[] uuid = getBytesFrom(UUID.randomUUID());
		String token = Base64.getEncoder().encodeToString(uuid);

		// Save information about the user
		User user = Mapper.mapFrom(new Credentials(firstName, lastName, ubn), token);
		DBResultMessage<Boolean> isAdded = userManager.add(user);
		return serializer.toJson(Mapper.mapFrom(isAdded));
	}

	@Override
	public String isValidToken(String token) {
		Gson serializer = new Gson();
		serializer.serializeNulls();
		DBResultMessage<Boolean> isValid = userManager.isValidToken(token);
		return serializer.toJson(Mapper.mapFrom(isValid));
	}

	/**
	 * Returns byte array from provided UUID.
	 * 
	 * @param uuid UUID that needs to be converted to byte array.
	 */
	private byte[] getBytesFrom(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}
}
