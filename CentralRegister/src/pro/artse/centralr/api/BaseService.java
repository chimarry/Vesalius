package pro.artse.centralr.api;

import javax.ws.rs.core.Response.Status;
import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.managers.IAuthorizationManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.util.UnauthorizedException;

public class BaseService {
	protected IAuthorizationManager authorizationManager;

	public BaseService() {
		try {
			authorizationManager = ManagerFactory.getAuthorizationManager();
		} catch (ServiceException e) {
			authorizationManager = new IAuthorizationManager() {
				@Override
				public CrResultMessage<Boolean> authorize(String token) {
					return null;
				}
			};
		}
	}

	public void authorize(String token) throws UnauthorizedException {
		CrResultMessage<Boolean> isValid = authorizationManager.authorize(token);
		if (isValid == null || isValid.getHttpStatusCode() != Status.ACCEPTED)
			throw new UnauthorizedException();
	}
}
