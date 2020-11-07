package pro.artse.centralr.api;

import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.arste.centralr.errorhandling.UnauthorizedException;
import pro.artse.centralr.managers.IAuthorizationManager;
import pro.artse.centralr.managers.ManagerFactory;

public class BaseService {
	protected IAuthorizationManager authorizationManager;

	public BaseService() {
		try {
			authorizationManager = ManagerFactory.getAuthorizationManager();
		} catch (ServiceException e) {
			authorizationManager = new IAuthorizationManager() {
				@Override
				public CrResultMessage<Boolean> authorize(String token) {
					return ErrorHandler.handle(e);
				}
			};
		}
	}

	public void authorize(String token) throws UnauthorizedException {
		CrResultMessage<Boolean> authorized = authorizationManager.authorize(token);

		// UserDTO is not authorized
		if (!authorized.isSuccess())
			throw new UnauthorizedException(authorized.getHttpStatusCode());
	}
}
