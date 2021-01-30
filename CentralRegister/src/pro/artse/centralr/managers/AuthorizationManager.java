package pro.artse.centralr.managers;

import java.rmi.RemoteException;

import javax.ws.rs.core.Response.Status;
import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.util.Mapper;
import pro.artse.tokenserver.services.TokenService;

public class AuthorizationManager implements IAuthorizationManager {

	private final TokenService tokenService = ManagerFactory.getTokenService();

	public AuthorizationManager() throws ServiceException {
	}

	/**
	 * Authorizes user if the token is valid (exist and is active).
	 * 
	 * @param token UserDTO's token used for validation.
	 * @return True if valid, false if not.
	 */
	@Override
	public CrResultMessage<Boolean> authorize(String token) {
		try {
			String isValid = tokenService.isValidToken(token);
			CrResultMessage<Boolean> resultMessage = Mapper.mapFrom(isValid, Boolean.class);
			if (resultMessage.getHttpStatusCode() == Status.NO_CONTENT)
				resultMessage.setHttpStatusCode(Status.UNAUTHORIZED);
			return resultMessage;
		} catch (RemoteException e) {
			// TODO: Add logger
			return ErrorHandler.handle(e);
		}
	}
}
