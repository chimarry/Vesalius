package pro.artse.user.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import pro.arste.chat.errorhandling.ErrorHandler;
import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.fileserver.rmi.IFileShare;
import pro.artse.fileserver.util.Compressor;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.MedicalDocument;
import pro.artse.user.util.Mapper;

public class FileServerManager implements IFileServerManager {

	public static final String RESOURCE_PATH = "resources";

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
			files.forEach(x -> {
				try {
					byte[] data = Files.readAllBytes(Paths.get(x.getAbsolutePath()));
					FSResultMessage<Boolean> isUploaded = fileShare.uploadFile(new BasicFileInfo(x.getName()),
							Compressor.compress(data), token, true);
					if (isUploaded.isSuccess())
						numberOfAddedFiles.incrementAndGet();
				} catch (IOException e) {
					ErrorHandler.handle(e);
				}
			});
			return new SUResultMessage<Integer>(new Integer(numberOfAddedFiles.intValue()), SUStatus.SUCCESS);
		} catch (RemoteException | NotBoundException e) {
			return pro.artse.user.errorhandling.ErrorHandler.handle(e);
		}
	}

	public SUResultMessage<List<MedicalDocument>> getUserFiles(String token) {
		System.setProperty("java.security.policy", RESOURCE_PATH + File.separator + "client_policy.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		List<MedicalDocument> medicalDocuments = new ArrayList<>();
		try {
			String name = "FileShare";
			Registry registry;
			registry = LocateRegistry.getRegistry(1099);
			IFileShare fileShare = (IFileShare) registry.lookup(name);
			FSResultMessage<List<BasicFileInfo>> files = fileShare.getUserFiles(token);
			if (files.isSuccess())
				medicalDocuments = files.getResult().stream().map(Mapper::mapFrom).collect(Collectors.toList());

			return new SUResultMessage<List<MedicalDocument>>(medicalDocuments,
					Mapper.mapFSStatus(files.getStatus().toString()), files.getMessage());
		} catch (RemoteException | NotBoundException e) {
			return pro.artse.user.errorhandling.ErrorHandler.handle(e);
		}
	}

	public SUResultMessage<byte[]> downloadFile(String fileName, String token) {
		System.setProperty("java.security.policy", RESOURCE_PATH + File.separator + "client_policy.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "FileShare";
			Registry registry;
			registry = LocateRegistry.getRegistry(1099);
			IFileShare fileShare = (IFileShare) registry.lookup(name);
			FSResultMessage<byte[]> data = fileShare.downloadFile(fileName, token, true);
			SUResultMessage<byte[]> compressedData = Mapper.mapFromFS(data);
			// Decompress bytes
			compressedData.setResult(Compressor.decompress(data.getResult()));
			return compressedData;
		} catch (IOException | NotBoundException | DataFormatException e) {
			return pro.artse.user.errorhandling.ErrorHandler.handle(e);
		}
	}

	@Override
	public SUResultMessage<Boolean> deleteDirectory(String token) {
		System.setProperty("java.security.policy", RESOURCE_PATH + File.separator + "client_policy.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "FileShare";
			Registry registry;
			registry = LocateRegistry.getRegistry(1099);
			IFileShare fileShare = (IFileShare) registry.lookup(name);
			FSResultMessage<Boolean> deleted = fileShare.deleteDirectory(token);
			return Mapper.mapFromFS(deleted);
		} catch (IOException | NotBoundException e) {
			return pro.artse.user.errorhandling.ErrorHandler.handle(e);
		}
	}
}
