package pro.artse.centralr.managers;

import java.rmi.RemoteException;
import java.util.List;

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
	public CrResultMessage<Boolean> unregister(String token) {
		DBResultMessage<Boolean> isDeactived = userManager.deactivate(token);
		return Mapper.mapFrom(isDeactived);
	}

	@Override
	public CrResultMessage<KeyUserInfoWrapper[]> getAll() throws ServiceException, RemoteException {
		TokenService tokenService = pro.artse.centralr.managers.ManagerFactory.geTokenService();
		String allTokens = tokenService.getAll();
		System.out.println(allTokens);
		return Mapper.mapFrom(allTokens, KeyUserInfoWrapper[].class);
	}
}
