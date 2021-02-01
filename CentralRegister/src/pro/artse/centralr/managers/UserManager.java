package pro.artse.centralr.managers;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.core.Response.Status;
import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.UserLocationDTO;
import pro.artse.tokenserver.services.TokenService;

public class UserManager implements IUserManager {
	private final pro.artse.dal.managers.IUserManager userManager = ManagerFactory.getUserManager();

	@Override
	public CrResultMessage<KeyUserInfoWrapper[]> getAll() throws ServiceException, RemoteException {
		TokenService tokenService = pro.artse.centralr.managers.ManagerFactory.getTokenService();
		String allTokens = tokenService.getAll();
		return Mapper.mapFrom(allTokens, KeyUserInfoWrapper[].class);
	}

	@Override
	public CrResultMessage<KeyUserInfoWrapper> search(String token) throws ServiceException, RemoteException {
		TokenService tokenService = pro.artse.centralr.managers.ManagerFactory.getTokenService();
		String user = tokenService.search(token);
		return Mapper.mapFrom(user, KeyUserInfoWrapper.class);
	}

	@Override
	public CrResultMessage<Boolean> blockUser(String token) {
		DBResultMessage<Boolean> isBlocked = userManager.blockUser(token);
		return Mapper.mapFrom(isBlocked);
	}

	@Override
	public CrResultMessage<Boolean> markUserAsInfected(String token, LocationWrapper location) {
		DBResultMessage<Boolean> isMarkedAsInfected = userManager.changeCovidStatus(token, 2);
		if (!isMarkedAsInfected.isSuccess())
			return Mapper.mapFrom(isMarkedAsInfected);
		markAsPotentiallyInfected(token, Mapper.mapToDTO(location));
		return new CrResultMessage<Boolean>(true, Status.OK);
	}

	private void markAsPotentiallyInfected(String token, LocationDTO location) {
		int meters = 2000;
		int timeInterval = 30;
		DBResultMessage<List<UserLocationDTO>> potentiallyInfected = userManager.markUsersAsPotentiallyInfected(token,
				location, meters, timeInterval);
		if (potentiallyInfected.isSuccess())
			potentiallyInfected.getResult().stream()
					.forEach(x -> markAsPotentiallyInfected(x.getToken(), x.getLocation()));
	}
}
