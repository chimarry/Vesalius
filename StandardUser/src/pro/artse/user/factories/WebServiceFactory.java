package pro.artse.user.factories;

import javax.xml.rpc.ServiceException;

import pro.artse.tokenserver.services.TokenService;
import pro.artse.tokenserver.services.TokenServiceServiceLocator;

/**
 * Factory for resolving dependencies.
 * @author Marija
 *
 */
public class WebServiceFactory {

	/**
	 * 
	 * @return Configured token service.
	 * @throws ServiceException
	 */
	public static TokenService getTokenService() throws ServiceException {
		TokenServiceServiceLocator locator = new TokenServiceServiceLocator();
		return locator.getTokenService();
	}
}
