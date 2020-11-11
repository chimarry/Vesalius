package pro.artse.centralr.managers;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.tokenserver.services.TokenService;

public class UserManager implements IUserManager {
	private final pro.artse.dal.managers.IUserManager userManager = ManagerFactory.getUserManager();

	@Override
	public CrResultMessage<KeyUserInfoWrapper[]> getAll() throws ServiceException, RemoteException {
		TokenService tokenService = pro.artse.centralr.managers.ManagerFactory.geTokenService();
		String allTokens = tokenService.getAll();
		return Mapper.mapFrom(allTokens, KeyUserInfoWrapper[].class);
	}

	@Override
	public CrResultMessage<KeyUserInfoWrapper> search(String token) throws ServiceException, RemoteException {
		TokenService tokenService = pro.artse.centralr.managers.ManagerFactory.geTokenService();
		String user = tokenService.search(token);
		return Mapper.mapFrom(user, KeyUserInfoWrapper.class);
	}

	@Override
	public CrResultMessage<Boolean> blockUser(String token) {
		DBResultMessage<Boolean> isBlocked = userManager.blockUser(token);
		return Mapper.mapFrom(isBlocked);
	}
}
