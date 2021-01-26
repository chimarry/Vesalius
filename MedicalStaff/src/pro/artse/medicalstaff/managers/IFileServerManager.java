package pro.artse.medicalstaff.managers;

import java.util.List;

import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.MedicalDocument;

public interface IFileServerManager {
	MSResultMessage<byte[]> downloadFile(String fileName, String token);

	MSResultMessage<List<MedicalDocument>> getUserFiles(String token);
}
