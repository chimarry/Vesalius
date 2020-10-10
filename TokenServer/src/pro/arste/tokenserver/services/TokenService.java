/**
 * 
 */
package pro.arste.tokenserver.services;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import pro.artse.tokenserver.models.Credentials;

/**
 * @author Marija Implements interface for managing tokens.
 */
public class TokenService implements ITokenService {

	/** Generates unique token based on provided user's credentials, 
	 *  saves that token to database and
	 *  returns it as base64 encoded string to the user.
	 *  @param credentials User credentials.
	 */
	@Override
	public String generateToken(Credentials credentials) {
		String token = "";
		byte[] uuid = getBytesFrom(UUID.fromString(credentials.getUBN()));
		token = Base64.getEncoder().encodeToString(uuid);
		return token;
	}

	/**
	 * Returns byte array from provided UUID.
	 * @param uuid  UUID that needs to be converted to byte array.
	 */
	private byte[] getBytesFrom(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

}
