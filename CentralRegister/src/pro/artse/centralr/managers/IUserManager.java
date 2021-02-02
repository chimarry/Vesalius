package pro.artse.centralr.managers;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.models.LocationWrapper;

public interface IUserManager {
	CrResultMessage<KeyUserInfoWrapper[]> getAll() throws ServiceException, RemoteException;

	CrResultMessage<KeyUserInfoWrapper> search(String token) throws ServiceException, RemoteException;

	CrResultMessage<Boolean> blockUser(String token);

	CrResultMessage<Boolean> unregister(String token);

	CrResultMessage<Boolean> markUserAsInfected(String token, LocationWrapper location);
}
