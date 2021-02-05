package pro.artse.user.serialization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.fasterxml.jackson.xml.XmlMapper;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.Notification;

public class XmlSerializer implements Serializer {

	private static final String EXTENSION = ".xml";

	@Override
	public SUResultMessage<Boolean> serialize(Notification notification) {
		String fileName = buildFileName(EXTENSION);
		XmlMapper xmlMapper = new XmlMapper();
		try {
			String xml = xmlMapper.writeValueAsString(notification);
			byte[] xmlData = xml.getBytes();
			Files.write(Paths.get(fileName), xmlData, StandardOpenOption.CREATE_NEW);
			return new SUResultMessage<Boolean>(true, SUStatus.SUCCESS);
		} catch (IOException e) {
			return handle(e);
		}
	}
}
