package pro.artse.user.notifications;

public final class SerializerFactory {
	private static int nextSerializer = 0;
	private static final int SERIALIZER_COUNT = 5;

	private static final Serializer[] serializers = new Serializer[] { new JavaSerializer(), new KryoSerializer(),
			new GsonSerializer(), new XmlSerializer(), new CsvSerializer() };

	public static Serializer getNextSerializer() {
		return serializers[(nextSerializer++) % SERIALIZER_COUNT];
	}
}
