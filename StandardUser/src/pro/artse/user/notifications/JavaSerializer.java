package pro.artse.user.notifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
public class JavaSerializer extends Serializer {

	private static final String EXTENSION = ".ser";

	/**
	 * Serializes notification in user's folder using default java serializer.
	 * 
	 * @param notification Notification to serialize.
	 * @return Success of serialization.
	 */
	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationStorage.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(notification);
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
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			Notification notification = (Notification) in.readObject();
			return new SUResultMessage<Notification>(notification, SUStatus.SUCCESS);
		} catch (FileNotFoundException e) {
			return handleNotificationError(e);
		} catch (IOException e) {
			return handleNotificationError(e);
		} catch (ClassNotFoundException e) {
			return handleNotificationError(e);
		}
	}
}
