package pro.artse.fileserver.rmi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

import pro.artse.fileserver.errorhandling.ErrorHandler;
import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.errorhandling.FSStatus;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.fileserver.util.Compressor;
import pro.artse.fileserver.util.DirectoryStructureBuilder;

public class FileShare implements IFileShare {

	public static String RESOURCE_PATH = "resources";

	public FileShare() throws RemoteException {
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", RESOURCE_PATH + File.separator + "server_policyfile.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			FileShare fileShare = new FileShare();
			IFileShare stub = (IFileShare) UnicastRemoteObject.exportObject(fileShare, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("FileShare", stub);
			System.out.println("File server started is started.");

		} catch (RemoteException e) {
			ErrorHandler.handle(e);
		}
	}

	@Override
	public FSResultMessage<Boolean> uploadFile(BasicFileInfo fileInfo, byte[] data, String token, boolean isCompressed)
			throws RemoteException {
		boolean isSaved = false;
		String userDirectoryPath = DirectoryStructureBuilder.buildUserDirectoryPath(token);
		System.out.println(userDirectoryPath);
		File userDirectory = new File(userDirectoryPath);
		if (!userDirectory.exists())
			userDirectory.mkdir();

		File fileToSave = new File(
				DirectoryStructureBuilder.buildPathForFile(userDirectoryPath, fileInfo.getFileName(), token));
		try {
			boolean isCreated = fileToSave.createNewFile();
			if (!isCreated)
				return new FSResultMessage<Boolean>(false, FSStatus.UNKNOWN_ERROR, "File could not been created.");
			byte[] dataToSave = data;
			if (isCompressed)
				dataToSave = Compressor.decompress(data);
			isSaved = saveFile(fileToSave, dataToSave);
		} catch (IOException | DataFormatException e) {
			return ErrorHandler.handle(e, false);
		}
		if (!isSaved)
			return new FSResultMessage<Boolean>(false, FSStatus.SERVER_ERROR, "Data is not saved.");
		return new FSResultMessage<Boolean>(FSStatus.SUCCESS);
	}

	@Override
	public FSResultMessage<List<BasicFileInfo>> getUserFiles(String token) throws RemoteException {
		List<BasicFileInfo> userFiles = new ArrayList<BasicFileInfo>();

		String userDirectoryPath = DirectoryStructureBuilder.buildUserDirectoryPath(token);
		try {
			Files.walk(Paths.get(userDirectoryPath)).filter(Files::isRegularFile).forEach(file -> {
				try {
					String name = DirectoryStructureBuilder.getFileName(file);
					BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
					userFiles.add(new BasicFileInfo(name, attr.size(), attr.creationTime()));
				} catch (IOException e) {
					ErrorHandler.handle(e);
				}
			});
			return new FSResultMessage<List<BasicFileInfo>>(userFiles, FSStatus.SUCCESS);
		} catch (IOException e1) {
			return ErrorHandler.handle(e1);
		}
	}

	@Override
	public FSResultMessage<byte[]> downloadFile(String fileName, String token, boolean compress)
			throws RemoteException {
		String userDirectoryPath = DirectoryStructureBuilder.buildUserDirectoryPath(token);
		String filePath = DirectoryStructureBuilder.buildPathForFile(userDirectoryPath, fileName, token);
		try {
			byte[] data = Files.readAllBytes(Paths.get(filePath));
			return new FSResultMessage<byte[]>(Compressor.compress(data), FSStatus.SUCCESS);
		} catch (IOException e) {
			return ErrorHandler.handle(e);
		}
	}

	private boolean saveFile(File file, byte[] data) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file, true)) {
			fos.write(data);
			return true;
		}
	}

	@Override
	public FSResultMessage<Boolean> deleteDirectory(String token) throws RemoteException {
		String userDirectoryPath = DirectoryStructureBuilder.buildUserDirectoryPath(token);
		try (Stream<Path> walk = Files.walk(Paths.get(userDirectoryPath))) {
			walk.sorted(Comparator.reverseOrder()).forEach(FileShare::deleteDirectoryJava8Extract);
			return new FSResultMessage<Boolean>(true, FSStatus.SUCCESS);
		} catch (IOException e) {
			return ErrorHandler.handle(e);
		}
	}

	// extract method to handle exception in lambda
	public static void deleteDirectoryJava8Extract(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}
}
