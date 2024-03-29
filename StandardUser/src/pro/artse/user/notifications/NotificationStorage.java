package pro.artse.user.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.util.ConfigurationUtil;

public final class NotificationStorage {

	public static final String DIRECTORY_FORMAT = ConfigurationUtil.get("notificationRootDirectory") + File.separator
			+ "%s";
	public static final String FILE_NAME_FORMAT = DIRECTORY_FORMAT + File.separator + "%s";

	public static final String IMAGE_DIRECTORY = "tumbnails";

	public static final String IMAGE_DIRECTORY_FORMAT = DIRECTORY_FORMAT + File.separator + IMAGE_DIRECTORY;

	public static String buildFileName(String notificationName, String token, String extension) {
		return String.format(FILE_NAME_FORMAT, token, notificationName + extension);
	}

	public static String buildDirectoryForImagesPath(String token) {
		File imagesDir = new File(String.format(IMAGE_DIRECTORY_FORMAT, token));
		if (!imagesDir.exists())
			imagesDir.mkdirs();
		return imagesDir.getPath();
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
			ErrorHandler.handle(e);
			return false;
		}
	}

	public static SUResultMessage<List<File>> getNotifications(String token) {
		ArrayList<File> notifications = new ArrayList<>();
		String userDirectoryPath = String.format(DIRECTORY_FORMAT, token);
		try {
			notifications = Files.walk(Paths.get(userDirectoryPath)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.filter(x -> x.isFile() && !x.getPath().contains(IMAGE_DIRECTORY))
					.collect(Collectors.toCollection(ArrayList<File>::new));
			return new SUResultMessage<List<File>>(notifications, SUStatus.SUCCESS);
		} catch (NoSuchFileException e) {
			return new SUResultMessage<List<File>>(notifications, SUStatus.SUCCESS, "Notification history is empty.");
		} catch (IOException e) {
			ErrorHandler.handle(e);
			return new SUResultMessage<List<File>>(notifications, SUStatus.SERVER_ERROR,
					"Notifications could not been read");
		}
	}
}
