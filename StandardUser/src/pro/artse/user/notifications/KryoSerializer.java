package pro.artse.user.notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;

import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

public class KryoSerializer implements Serializer {

	private static final String EXTENSION = ".dat";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationDirectory.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);

		try (Output out = new Output(new FileOutputStream(file))) {
			Kryo kryo = new Kryo();
			kryo.register(Notification.class);
			kryo.writeClassAndObject(out, notification);
			out.flush();
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (KryoException e) {
			return handle(e);
		} catch (FileNotFoundException e) {
			return handle(e);
		}
	}
}
