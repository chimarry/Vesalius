package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.UserDTO;

/**
 * @author Marija Declares methods responsible for working with user's data.
 */
public interface IUserManager {

	/**
	 * Saves information about an user.
	 * 
	 * @param user Information about the user, where UNB, first name, last name are
	 *             mandatory.
	 * @return True if user is added, false if not.
	 */
	DBResultMessage<Boolean> add(UserDTO user);

	/**
	 * Deactivates user's token.
	 * 
	 * @param token Unique identifier of an user.
	 * @return True if token is deactivated, false if not.
	 */
	DBResultMessage<Boolean> deactivate(String token);

	/**
	 * Checks if token is valid.
	 * 
	 * @param token Token to validate.
	 * @return True if valid, false if not.
	 */
	DBResultMessage<Boolean> isValidToken(String token);

	/**
	 * Selects only token from a users that are active.
	 * 
	 * @return List of all active tokens.
	 */
	DBResultMessage<List<KeyUserInfoDTO>> getAllAllowedInformation();

	DBResultMessage<KeyUserInfoDTO> search(String token);
}
