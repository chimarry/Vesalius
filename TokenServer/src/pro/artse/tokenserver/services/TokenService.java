package pro.artse.tokenserver.services;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.UserDTO;
import pro.artse.tokenserver.errorhandling.TSResultMessage;
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
	 * @param credentials UserDTO credentials.
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
		UserDTO user = Mapper.mapFrom(new Credentials(firstName, lastName, ubn), token);
		DBResultMessage<Boolean> isAdded = userManager.add(user);
		TSResultMessage<Boolean> resultMessage = Mapper.mapFrom(isAdded);
		return serializer.toJson(new TSResultMessage<String>(token, resultMessage.getStatus(),resultMessage.getMessage()));
	}

	@Override
	public String isValidToken(String token) {
		Gson serializer = new Gson();
		serializer.serializeNulls();

		DBResultMessage<Boolean> isValid = userManager.isValidToken(token);
		return serializer.toJson(Mapper.mapFrom(isValid));
	}

	@Override
	public String search(String token) {
		Gson serializer = new Gson();
		serializer.serializeNulls();

		DBResultMessage<KeyUserInfoDTO> user = userManager.search(token);
		return serializer.toJson(Mapper.mapFrom(user));
	}

	@Override
	public String getAll() {
		Gson serializer = new Gson();
		serializer.serializeNulls();

		DBResultMessage<List<KeyUserInfoDTO>> allUsers = userManager.getAllAllowedInformation();
		return serializer.toJson(Mapper.mapFrom(allUsers));
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
