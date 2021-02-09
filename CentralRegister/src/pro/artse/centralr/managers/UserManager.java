package pro.artse.centralr.managers;

import java.rmi.RemoteException;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.NotificationDTO;
import pro.artse.dal.models.UserLocationDTO;
import pro.artse.tokenserver.services.TokenService;

public class UserManager implements IUserManager {
	private final pro.artse.dal.managers.IUserManager userManager = pro.artse.dal.factory.ManagerFactory
			.getUserManager();
	private final INotificationManager notificationManager = ManagerFactory.getNotificationManager();

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
	public CrResultMessage<Boolean> unregister(String token) {
		DBResultMessage<Boolean> isUnregistered = userManager.unregister(token);
		return Mapper.mapFrom(isUnregistered);
	}

	@Override
	public CrResultMessage<Boolean> markUserAsInfected(String token, LocationWrapper location) {
		DBResultMessage<Boolean> isMarkedAsInfected = userManager.changeCovidStatus(token, 2);
		if (!isMarkedAsInfected.isSuccess())
			return Mapper.mapFrom(isMarkedAsInfected);
		markAsPotentiallyInfected(token, token, Mapper.mapToDTO(location));
		return new CrResultMessage<Boolean>(true, Status.OK);
	}

	private void markAsPotentiallyInfected(String infectionSourceToken, String infectedPersonToken,
			LocationDTO location) {
		// TODO: Get n, k, p
		int meters = 20000;
		int timeInterval = 5;
		saveNotification(infectedPersonToken, infectionSourceToken, location);
		DBResultMessage<List<UserLocationDTO>> potentiallyInfected = userManager
				.markUsersAsPotentiallyInfected(infectionSourceToken, location, meters, timeInterval);
		if (potentiallyInfected.isSuccess())
			potentiallyInfected.getResult().stream()
					.forEach(x -> markAsPotentiallyInfected(infectionSourceToken, x.getToken(), x.getLocation()));
	}

	private void saveNotification(String token, String fromWhomToken, LocationDTO location) {
		NotificationDTO notification = new NotificationDTO(token, fromWhomToken, location);
		CrResultMessage<Boolean> isAdded = notificationManager.add(Mapper.mapToWrapper(notification));
		// TODO: Log errors
	}
}
