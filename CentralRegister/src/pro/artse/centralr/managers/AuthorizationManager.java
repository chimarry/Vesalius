package pro.artse.centralr.managers;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.util.Mapper;
import pro.artse.tokenserver.services.TokenService;

public class AuthorizationManager implements IAuthorizationManager {

	private final TokenService tokenService = ManagerFactory.geTokenService();

	public AuthorizationManager() throws ServiceException {
	}

	/**
	 * Authorizes user if the token is valid (exist and is active).
	 * 
	 * @param token User's token used for validation.
	 * @return True if valid, false if not.
	 */
	@Override
	public CrResultMessage<Boolean> authorize(String token) {
		try {
			String isValid = tokenService.isValidToken(token);
			return Mapper.mapFrom(isValid, Boolean.class);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
}
