package pro.artse.user.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

public class  GsonSerializer extends Serializer {

	private static final String EXTENSION = ".json";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationStorage.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(notification).getBytes();
		try {
			Files.write(Paths.get(file.getPath()), jsonData, StandardOpenOption.WRITE);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}

	@Override
	protected boolean hasExtension(String extension) {
		return EXTENSION.equals(extension);
	}

	@Override
	public SUResultMessage<Notification> deserialize(File file) {
		try {
			Gson gson = new Gson();
			byte[] jsonData = Files.readAllBytes(Paths.get(file.getPath()));
			Notification notification = gson.fromJson(new String(jsonData), Notification.class);
			return new SUResultMessage<Notification>(notification, SUStatus.SUCCESS);
		} catch (JsonSyntaxException e) {
			return handleNotificationError(e);
		} catch (IOException e) {
			return handleNotificationError(e);
		}
	}
}
