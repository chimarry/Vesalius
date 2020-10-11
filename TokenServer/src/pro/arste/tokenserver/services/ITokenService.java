package pro.arste.tokenserver.services;


import pro.arste.common.result.ResultMessage;

/**
 * @author Marija Provided interface for managing tokens.
 */
public interface ITokenService {
	/**
	 * Generates unique token based on provided user's credentials and returns it to
	 * the user.
	 */
	String generateToken(String firstName, String lastName, String ubn);
}
