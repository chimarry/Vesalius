package pro.arste.chat;

public class ChatServer {
	private static final String KEY_STORE_PATH = "keystore.jks";
	private static final String KEY_STORE_PASSWORD = "securemdp";

	public static void main(String... args) {
		secure();

		openConnection(new AvailabilityRunnable());
		openConnection(new UnicastChatConnectionRunnable());
	}

	public static void openConnection(Runnable typeOfConnection) {
		Thread thread = new Thread(typeOfConnection);
		thread.start();
	}

	public static void secure() {
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
	}
}
