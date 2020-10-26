package pro.artse.centralr.managers;

import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IUserManager;

public class AuthorizationManager implements IAuthorizationManager {

	private final IUserManager userManager = ManagerFactory.getUserManager();

	/**
	 * Authorizes user if the token is valid (exist and is active).
	 * 
	 * @param token User's token used for validation.
	 * @return True if valid, false if not.
	 */
	@Override
	public boolean authorize(String token) {
		return userManager.isValidToken(token);
	}
}
