package pro.artse.centralr.managers;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.KeyUserInfoWrapper;

public interface IUserManager {
	CrResultMessage<Boolean> unregister(String token);

	CrResultMessage<KeyUserInfoWrapper[]> getAll() throws ServiceException, RemoteException;

	CrResultMessage<KeyUserInfoWrapper> search(String token) throws ServiceException, RemoteException;
}
