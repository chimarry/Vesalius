package pro.artse.user.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import pro.artse.fileserver.rmi.FileShare;
import pro.artse.user.models.Notification;
import pro.artse.user.models.User;
import pro.artse.user.util.ConfigurationUtil;

public final class NotificationDirectory {

	public static final String DIRECTORY_FORMAT = ConfigurationUtil.get("notificationRootDirectory") + File.separator
			+ "%s";
	public static final String FILE_NAME_FORMAT = DIRECTORY_FORMAT + File.separator + "%s";

	public static String buildFileName(String notificationName, String token, String extension) {
		return String.format(FILE_NAME_FORMAT, token, notificationName + extension);
	}

	public static boolean createNotificationDirectory(String token) {
		File directory = new File(String.format(DIRECTORY_FORMAT, token));
		if (!directory.exists())
			return directory.mkdirs();
		return true;
	}

	public static File createNotificationFile(String token, String notificationName, String extension) {
		createNotificationDirectory(token);
		File file = new File(buildFileName(notificationName, token, extension));
		try {
			if (!file.exists())
				file.createNewFile();
			return file;
		} catch (IOException e) {
			return file;
		}
	}

	public static boolean deleteNotificationDirectory(String token) {
		String userDirectoryPath = String.format(DIRECTORY_FORMAT, token);
		try {
			Files.walk(Paths.get(userDirectoryPath)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.forEach(File::delete);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
