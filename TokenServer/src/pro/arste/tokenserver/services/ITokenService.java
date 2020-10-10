package pro.arste.tokenserver.services;

import pro.artse.tokenserver.models.Credentials;

/**
 * @author Marija
 * Provided interface for managing tokens.
 */
public interface ITokenService {
	/** Generates unique token based on provided user's credentials and
	 *  returns it to the user.
	 */
		String generateToken(Credentials credentials);
}
