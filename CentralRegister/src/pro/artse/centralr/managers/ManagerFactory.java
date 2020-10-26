package pro.artse.centralr.managers;

/**
 * Resolves dependencies.
 * 
 * @author Marija
 *
 */
public final class ManagerFactory {

	/**
	 * Creates implementation for IActivityLogManager.
	 * 
	 * @return @see ActivityLogManager
	 */
	public static IActivityLogManager getActivityLogManager() {
		return new ActivityLogManager();
	}

	/**
	 * Creates implementation for IAuthorizationManager.
	 * 
	 * @return @see AuthorizationManager
	 */
	public static IAuthorizationManager getAuthorizationManager() {
		return new AuthorizationManager();
	}

	/**
	 * Creates implementation for IUserManager.
	 * 
	 * @return @see UserManager
	 */
	public static IUserManager getUserManager() {
		return new UserManager();
	}
}
