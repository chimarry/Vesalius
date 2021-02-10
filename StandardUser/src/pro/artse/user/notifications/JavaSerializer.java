package pro.artse.user.notifications;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

/**
 * Enables serialization of an object using default java serializer.
 * 
 * @author Marija
 *
 */
public class JavaSerializer implements Serializer {

	private static final String EXTENSION = ".ser";

	/**
	 * Serializes notification in user's folder using default java serializer.
	 * 
	 * @param notification Notification to serialize.
	 * @return Success of serialization.
	 */
	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationDirectory.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(file);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
			return handle(e);
		}
	}
}
