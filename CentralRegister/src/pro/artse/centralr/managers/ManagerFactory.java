package pro.artse.centralr.managers;

import javax.xml.rpc.ServiceException;

import pro.artse.tokenserver.services.TokenService;
import pro.artse.tokenserver.services.TokenServiceServiceLocator;

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
	 * @throws ServiceException
	 */
	public static IAuthorizationManager getAuthorizationManager() throws ServiceException {
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

	/**
	 * Creates implementation for ILocationManager.
	 * 
	 * @return @see LocationManager
	 */
	public static ILocationManager getLocationManager() {
		return new LocationManager();
	}

	/**
	 * Creates implementation for INotificationManager.
	 * 
	 * @return @see NotificationManager
	 */
	public static INotificationManager getNotificationManager() {
		return new NotificationManager();
	}

	/**
	 * @return Configured token service.
	 * @throws ServiceException
	 */
	public static TokenService getTokenService() throws ServiceException {
		TokenServiceServiceLocator locator = new TokenServiceServiceLocator();
		return locator.getTokenService();
	}
}
