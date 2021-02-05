package pro.artse.user.serialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
		String fileName = buildFileName(EXTENSION);
		try (Output out = new Output(new FileOutputStream(fileName))) {
			Kryo kryo = new Kryo();
			kryo.register(Notification.class);
			kryo.writeClassAndObject(out, notification);
			out.flush();
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (KryoException e) {
			return ErrorHandler.handle(e);
		} catch (FileNotFoundException e) {
			return ErrorHandler.handle(e);
		}
	}
}
