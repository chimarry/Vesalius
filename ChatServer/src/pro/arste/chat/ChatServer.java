package pro.arste.chat;

public class ChatServer {
	public static void main(String... args) {
		openConnection(new AvailabilityRunnable());
		openConnection(new UnicastChatConnectionRunnable());
	}

	public static void openConnection(Runnable typeOfConnection) {
		Thread thread = new Thread(typeOfConnection);
		thread.start();
	}
}
