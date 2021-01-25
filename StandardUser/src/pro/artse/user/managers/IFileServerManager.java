package pro.artse.user.managers;

import java.io.File;
import java.util.List;

import pro.artse.user.errorhandling.SUResultMessage;

public interface IFileServerManager {
	SUResultMessage<Integer> sendFiles(List<File> files, String token);
}
