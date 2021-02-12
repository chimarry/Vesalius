package pro.artse.user.notifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

public class KryoSerializer extends Serializer {

	private static final String EXTENSION = ".dat";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		File file = NotificationStorage.createNotificationFile(notification.getToken(), notification.getName(),
				EXTENSION);

		try (Output out = new Output(new FileOutputStream(file))) {
			Kryo kryo = new Kryo();
			kryo.register(Notification.class, new NotificationKryoCustomSerializer());
			kryo.writeObject(out, notification);
			out.flush();
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (KryoException e) {
			return handle(e);
		} catch (FileNotFoundException e) {
			return handle(e);
		}
	}

	@Override
	protected boolean hasExtension(String extension) {
		return EXTENSION.equals(extension);
	}

	@Override
	public SUResultMessage<Notification> deserialize(File file) {
		Notification notification = null;
		try (Input in = new Input(new FileInputStream(file))) {
			Kryo kryo = new Kryo();
			kryo.register(Notification.class, new NotificationKryoCustomSerializer());
			notification = kryo.readObject(in, Notification.class);
			return new SUResultMessage<Notification>(notification, SUStatus.SUCCESS);
		} catch (KryoException e) {
			return handleNotificationError(e);
		} catch (FileNotFoundException e) {
			return handleNotificationError(e);
		}
	}
}
