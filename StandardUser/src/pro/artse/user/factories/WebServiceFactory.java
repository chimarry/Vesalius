package pro.artse.user.factories;

import javax.xml.rpc.ServiceException;

import pro.artse.tokenserver.services.TokenService;
import pro.artse.tokenserver.services.TokenServiceServiceLocator;
import pro.artse.user.centralr.services.ActivityLogService;
import pro.artse.user.centralr.services.IActivityLogService;
import pro.artse.user.centralr.services.ILocationService;
import pro.artse.user.centralr.services.IUserService;
import pro.artse.user.centralr.services.LocationService;
import pro.artse.user.centralr.services.UserService;
import pro.artse.user.chat.ChatService;
import pro.artse.user.chat.IChatService;

/**
 * Factory for resolving dependencies.
 * 
 * @author Marija
 *
 */
public class WebServiceFactory {

	/**
	 * @return Configured token service.
	 * @throws ServiceException
	 */
	public static TokenService getTokenService() throws ServiceException {
		TokenServiceServiceLocator locator = new TokenServiceServiceLocator();
		return locator.getTokenService();
	}

	public static IActivityLogService getActivityLogService() {
		return new ActivityLogService();
	}

	public static IChatService getChatService() {
		return new ChatService();
	}

	public static ILocationService getLocationService() {
		return new LocationService();
	}

	public static IUserService getUserService() {
		return new UserService();
	}
}
