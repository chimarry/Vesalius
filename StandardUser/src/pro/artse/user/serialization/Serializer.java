package pro.artse.user.serialization;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Notification;
import pro.artse.user.models.User;

public interface Serializer {
	String DIRECTORY_FORMAT = "notifications//%s//%s";

	SUResultMessage<Boolean> serialize(Notification notification);

	default String buildFileName(String extension) {
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		String fileName = Base64.getEncoder().encodeToString(bb.array()) + extension;
		return String.format(DIRECTORY_FORMAT, User.getInstance().getToken(), fileName);
	}

	default SUResultMessage<Boolean> handle(IOException e) {
		SUResultMessage<Boolean> message = ErrorHandler.handle(e);
		message.setMessage("Notification is not saved.");
		return message;
	}
}
