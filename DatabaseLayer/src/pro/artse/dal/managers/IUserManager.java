package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.UserDTO;
import pro.artse.dal.models.UserLocationDTO;

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
	DBResultMessage<Boolean> blockUser(String token);

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

	/**
	 * Finds and returns user identified by provided token.
	 * 
	 * @param token User's identifier
	 * @return User's information that can be seen as public
	 */

	DBResultMessage<KeyUserInfoDTO> search(String token);

	/**
	 * Changes user's covid status.
	 * 
	 * @param token       Unique identifier for the user
	 * @param covidStatus New covid status
	 * @return True if success, false if not.
	 */

	DBResultMessage<Boolean> changeCovidStatus(String token, int covidStatus);

	/**
	 * Mark user as infected or potentially infected. Every other human who was in
	 * contact with infected person, will be marked as potentially infected. Contact
	 * is defined by location of first infected person, by distance and time
	 * interval.
	 * 
	 * @param token               Unique identifier of a user
	 * @param locationOfInfection Where did user get Covid-19?
	 * @return List of potentially infected users
	 */

	DBResultMessage<List<UserLocationDTO>> markUsersAsPotentiallyInfected(String token, LocationDTO locationOfInfection,
			int distanceInMeters, int timeInterval);

	DBResultMessage<Boolean> unregister(String token);
}
