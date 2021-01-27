package pro.artse.medicalstaff.managers;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import pro.artse.fileserver.errorhandling.FSResultMessage;
import pro.artse.fileserver.models.BasicFileInfo;
import pro.artse.fileserver.rmi.IFileShare;
import pro.artse.fileserver.util.Compressor;
import pro.artse.medicalstaff.errorhandling.ErrorHandler;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.MedicalDocument;
import pro.artse.medicalstaff.util.Mapper;

public class FileServerManager implements IFileServerManager {

	public static final String RESOURCE_PATH = "resources";

	public MSResultMessage<List<MedicalDocument>> getUserFiles(String token) {
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

			return new MSResultMessage<List<MedicalDocument>>(medicalDocuments,
					Mapper.mapFSStatus(files.getStatus().toString()), files.getMessage());
		} catch (RemoteException | NotBoundException e) {
			return ErrorHandler.handle(e);
		}
	}

	public MSResultMessage<byte[]> downloadFile(String fileName, String token) {
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
			MSResultMessage<byte[]> compressedData = Mapper.mapFromFS(data);
			compressedData.setResult(Compressor.decompress(data.getResult()));
			return compressedData;
		} catch (IOException | NotBoundException | DataFormatException e) {
			return ErrorHandler.handle(e);
		}
	}
}
