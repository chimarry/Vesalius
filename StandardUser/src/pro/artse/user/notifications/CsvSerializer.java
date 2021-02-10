package pro.artse.user.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;
import pro.artse.user.models.User;

public class CsvSerializer implements Serializer {

	private static final String EXTENSION = ".csv";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationDirectory.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);
		// TODO: check this
		String[] notificationArray = new String[10];
		byte[] csvData = Stream.of(notificationArray).map(this::escapeSpecialCharacters)
				.collect(Collectors.joining(",")).getBytes();
		try {
			Files.write(Paths.get(file.getPath()), csvData, StandardOpenOption.WRITE);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}

	private String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}
}
