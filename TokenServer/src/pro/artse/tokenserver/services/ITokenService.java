package pro.artse.tokenserver.services;

/**
 * @author Marija Provided interface for managing tokens.
 */
public interface ITokenService {
	/**
	 * Generates unique token based on provided user's credentials and returns it to
	 * the user.
	 */
	String generateToken(String firstName, String lastName, String ubn);

	/**
	 * Checks validity of a token.
	 * 
	 * @param token
	 * @return
	 */
	String isValidToken(String token);

	/**
	 * Finds and returns active tokens as unique user identifiers. It also returns
	 * additional information, such as covid status of an user.
	 * 
	 * @return Json representation of an array of Users
	 */
	String getAll();

	/**
	 * Finds and returns user based on specified token.
	 * 
	 * @return Json representation of basic user information (token and covid
	 *         status).
	 */
	String search(String token);
}
