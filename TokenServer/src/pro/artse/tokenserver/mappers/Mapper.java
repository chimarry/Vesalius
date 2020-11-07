package pro.artse.tokenserver.mappers;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.UserDTO;
import pro.artse.tokenserver.errorhandling.TSResultMessage;
import pro.artse.tokenserver.errorhandling.TSStatus;
import pro.artse.tokenserver.models.Credentials;

/**
 * Responsible for mappings between projects.
 * 
 * @author Marija
 *
 */
public final class Mapper {

	/**
	 * Creates new user using credentials and token. No validation is provided.
	 * 
	 * @param credentials Details about the user.
	 * @param token       UserDTO's generated token.
	 * @return Created user.
	 */
	public static UserDTO mapFrom(Credentials credentials, String token) {
		KeyUserInfoDTO info = new KeyUserInfoDTO(token, 0);
		UserDTO user = new UserDTO(info, credentials.getFirstName(), credentials.getLastName(), credentials.getUBN());
		return user;
	}

	public static <T> TSResultMessage<T> mapFrom(DBResultMessage<T> resultMessage) {
		TSResultMessage<T> mappedResultMessage = new TSResultMessage<T>(mapFrom(resultMessage.getStatus()));
		mappedResultMessage.setMessage(resultMessage.getMessage());
		mappedResultMessage.setResult(resultMessage.getResult());
		return mappedResultMessage;
	}

	private static TSStatus mapFrom(DbStatus status) {
		switch (status) {
		case INVALID_DATA:
			return TSStatus.INVALID_DATA;
		case SUCCESS:
			return TSStatus.SUCCESS;
		case SERVER_ERROR:
			return TSStatus.SERVER_ERROR;
		case NOT_FOUND:
			return TSStatus.NOT_FOUND;
		case EXISTS:
			return TSStatus.EXISTS;
		case UNKNOWN_ERROR:
			return TSStatus.UNKNOWN_ERROR;
		default:
			return TSStatus.UNKNOWN_ERROR;
		}
	}
}
