package pro.artse.user.serialization;

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
		String fileName = buildFileName(EXTENSION);
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
			out.writeObject(fileName);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}
}
