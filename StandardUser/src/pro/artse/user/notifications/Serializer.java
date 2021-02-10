package pro.artse.user.notifications;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Notification;

public interface Serializer {
	SUResultMessage<Boolean> serialize(Notification notification);

	default SUResultMessage<Boolean> handle(Exception e) {
		SUResultMessage<Boolean> message = ErrorHandler.handle(e);
		message.setMessage("Notification is not saved.");
		return message;
	}

}
