package pro.artse.fileserver.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;

public interface IFileShare extends Remote {
	FSResultMessage<List<BasicFileInfo>> getUserFiles(String token) throws RemoteException;

	FSResultMessage<Boolean> uploadFile(BasicFileInfo fileInfo, byte[] data, String token, boolean isCompressed)
			throws RemoteException;

	FSResultMessage<byte[]> downloadFile(String fileName, String token, boolean compress) throws RemoteException;

	FSResultMessage<Boolean> deleteDirectory(String token) throws RemoteException;
}
