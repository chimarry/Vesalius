package pro.artse.tokenserver.mappers;

import pro.artse.dal.models.BasicUserInfo;
import pro.artse.dal.models.User;
import pro.artse.tokenserver.models.Credentials;

/**
 * Responsible for mappings between projects.
 * @author Marija
 *
 */
public final class Mapper {

	/**
	 * Creates new user using credentials and token.
	 * No validation is provided.
	 * @param credentials Details about the user.
	 * @param token User's generated token.
	 * @return Created user.
	 */
	public static User mapFrom(Credentials credentials, String token) {
		BasicUserInfo info = new BasicUserInfo(token);
		User user = new User(info, credentials.getFirstName(), credentials.getLastName(), credentials.getUBN());
		return user;
	}
}
