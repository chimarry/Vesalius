package pro.artse.user.notifications;

import java.io.File;
import java.util.List;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Notification;

public abstract class Serializer {
	public SUResultMessage<Boolean> serializeNotification(Notification notification) {
		NotificationImageDrawer.drawText(notification);
		return serialize(notification);
	}

	public abstract SUResultMessage<Boolean> serialize(Notification notification);

	public abstract SUResultMessage<Notification> deserialize(File file);

	protected abstract boolean hasExtension(String extension);

	protected SUResultMessage<Boolean> handle(Exception e) {
		SUResultMessage<Boolean> message = ErrorHandler.handle(e);
		message.setMessage("Notification is not saved.");
		return message;
	}

	protected SUResultMessage<Notification> handleNotificationError(Exception e) {
		SUResultMessage<Notification> message = ErrorHandler.handle(e);
		message.setMessage("Notification is not read.");
		return message;
	}
}
