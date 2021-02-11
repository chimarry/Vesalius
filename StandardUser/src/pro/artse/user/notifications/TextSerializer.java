package pro.artse.user.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Location;
import pro.artse.user.models.Notification;

public class TextSerializer extends Serializer {

	private static final String EXTENSION = ".txt";
	private static final String WORD_SEPARATOR = "#";
	private static final String EMPTY_STRING = "";
	private static final int FIRST_LINE = 0;

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationStorage.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);
		String line = splitNotification(notification).stream().reduce(EMPTY_STRING, (a, b) -> a + WORD_SEPARATOR + b);
		try {
			Files.write(Paths.get(file.getPath()), line.getBytes(Charset.forName("UTF8")), StandardOpenOption.WRITE);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}

	@Override
	public SUResultMessage<Notification> deserialize(File file) {
		try {
			String data = Files.readAllLines(Paths.get(file.getPath())).get(FIRST_LINE);
			String[] attributes = new String(data).split(WORD_SEPARATOR);
			Notification notification = fromLine(attributes);
			return new SUResultMessage<Notification>(notification, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handleNotificationError(e);
		}
	}

	@Override
	protected boolean hasExtension(String extension) {
		return EXTENSION.equals(extension);
	}

	private ArrayList<String> splitNotification(Notification notification) {
		ArrayList<String> notificationArrayList = new ArrayList<>();
		notificationArrayList.add(notification.getName());
		notificationArrayList.add(notification.getToken());
		notificationArrayList.add(notification.getFromWhomToken());
		notificationArrayList.add(notification.getLocation().getSince());
		notificationArrayList.add(notification.getLocation().getUntil());
		notificationArrayList.add(String.valueOf(notification.getLocation().getLongitude()));
		notificationArrayList.add(String.valueOf(notification.getLocation().getLatitude()));
		return notificationArrayList;
	}

	private Notification fromLine(String[] attibutes) {
		Notification notification = new Notification();
		notification.setName(attibutes[1]);
		notification.setToken(attibutes[2]);
		notification.setFromWhomToken(attibutes[3]);
		Location location = new Location();
		location.setSince(attibutes[4]);
		location.setUntil(attibutes[5]);
		location.setLongitude(Double.valueOf(attibutes[6]));
		location.setLatitude(Double.valueOf(attibutes[7]));
		notification.setLocation(location);
		return notification;
	}
}
