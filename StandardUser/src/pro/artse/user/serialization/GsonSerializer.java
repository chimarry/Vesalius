package pro.artse.user.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

public class GsonSerializer implements Serializer {

	private static final String EXTENSION = ".json";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		String fileName = buildFileName(EXTENSION);
		Gson gson = new Gson();
		byte[] jsonData = gson.toJson(notification).getBytes();
		try {
			Files.write(Paths.get(fileName), jsonData, StandardOpenOption.CREATE);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}
}
