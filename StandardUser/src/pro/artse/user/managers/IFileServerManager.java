package pro.artse.user.managers;

import java.io.File;
import java.util.List;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.MedicalDocument;

public interface IFileServerManager {
	SUResultMessage<Integer> sendFiles(List<File> files, String token);

	SUResultMessage<byte[]> downloadFile(String fileName, String token);

	SUResultMessage<List<MedicalDocument>> getUserFiles(String token);

	SUResultMessage<Boolean> deleteDirectory(String token);
}
