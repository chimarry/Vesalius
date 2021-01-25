package pro.artse.fileserver.util;

import java.nio.file.Path;

public class DirectoryStructureBuilder {
	public static final String ROOT_DIRECTORY_PATH = ConfigurationUtil.get("rootDirectoryPath");
	public static final String USER_DIRECTORY_PATH_FORMAT = "%s//%s";
	public static final String USER_FILE_PATH_FORMAT = "%s//%s";
	public static final String FILE_NAME_FORMAT = "%s";

	public static String buildUserDirectoryPath(String token) {
		return String.format(USER_DIRECTORY_PATH_FORMAT, ROOT_DIRECTORY_PATH, token);
	}

	public static String buildPathForFile(String directoryPath, String fileName, String token) {
		String newFileName = String.format(FILE_NAME_FORMAT, fileName);
		return String.format(USER_FILE_PATH_FORMAT, directoryPath, newFileName);
	}

	public static String getFileName(Path path) {
		return path.getFileName().toString();
	}
}
