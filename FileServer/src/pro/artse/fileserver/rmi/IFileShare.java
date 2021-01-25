package pro.artse.fileserver.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;

public interface IFileShare extends Remote {
	FSResultMessage<Boolean> uploadFile(BasicFileInfo fileInfo, byte[] data, String token) throws RemoteException;
	FSResultMessage<List<BasicFileInfo>> getUserFiles(String token) throws RemoteException;
	FSResultMessage<byte[]> downloadFile(String fileName, String token) throws RemoteException;
}
