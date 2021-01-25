package pro.artse.user.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.fileserver.rmi.IFileShare;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;

public class FileServerManager implements IFileServerManager {

	public static final String RESOURCE_PATH = "resources";

	public FileServerManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SUResultMessage<Integer> sendFiles(List<File> files, String token) {
		System.setProperty("java.security.policy", RESOURCE_PATH + File.separator + "client_policy.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		AtomicInteger numberOfAddedFiles = new AtomicInteger(0);
		try {
			String name = "FileShare";
			Registry registry;
			registry = LocateRegistry.getRegistry(1099);
			IFileShare fileShare = (IFileShare) registry.lookup(name);
			files.parallelStream().forEach(x -> {
				try {
					byte[] data = Files.readAllBytes(Paths.get(x.getAbsolutePath()));
					FSResultMessage<Boolean> isUploaded = fileShare.uploadFile(new BasicFileInfo(x.getName()), data,
							token);
					if (isUploaded.isSuccess())
						numberOfAddedFiles.incrementAndGet();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			return new SUResultMessage<Integer>(new Integer(numberOfAddedFiles.intValue()), SUStatus.SUCCESS);
		} catch (RemoteException | NotBoundException e) {
			return pro.artse.user.errorhandling.ErrorHandler.handle(e);
		}
	}
}
