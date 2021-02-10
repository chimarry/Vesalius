package pro.artse.user.notifications;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public final class SerializerFactory {
	private static int nextSerializer = 0;
	private static final int SERIALIZER_COUNT = 5;

	private static final Serializer[] serializers = new Serializer[] { new JavaSerializer(), new KryoSerializer(),
			new GsonSerializer(), new XmlSerializer(), new CsvSerializer() };

	public static Serializer getNextSerializer() {
		return serializers[(nextSerializer++) % SERIALIZER_COUNT];
	}

	public static Serializer getBasedOnExtension(String fileName) throws NoSuchElementException {
		int extensionBeginIndex = fileName.lastIndexOf('.');
		String extension = fileName.substring(extensionBeginIndex);
		return Stream.of(serializers).filter(serializer -> serializer.hasExtension(extension)).findFirst().get();
	}
}
