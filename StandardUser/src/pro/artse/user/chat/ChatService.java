package pro.artse.user.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;
import pro.artse.user.errorhandling.ErrorHandler;

public class ChatService implements IChatService {
	private static final String TRUST_STORE_PATH = "C:\\Users\\Vasic\\Desktop\\keystore.jks";
	private static final String TRUST_STORE_PASSWORD = "securemdp";

	private Boolean isFinished = false;
	private Boolean isNewCommunication = true;

	private SSLSocket client;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	private ISubscriber subscriber;

	@Override
	public void sendMessage(String text) {
		if (isNewCommunication || client.isClosed()) {
			openConnection();
		}
		if (!isFinished) {
			printWriter.println(text);
		}
	}

	@Override
	public void receiveMessage() throws IOException {
		String message;
		try {
			message = bufferedReader.readLine();
			if (isFlag(message)) {
				printWriter.println(message);
			} else if (message.equals(ConfigurationUtil.get("closeFlag"))) {
				closeConnection();
			} else
				subscriber.notify(message);
		} catch (IOException e) {
			closeConnection();
			ErrorHandler.handle(e);
			throw e;
		}
	}

	@Override
	public void register(ISubscriber subscriber) {
		this.subscriber = subscriber;
	}

	private Boolean isFlag(String message) {
		return message == null || message.equals(ConfigurationUtil.get("endFlag"))
				|| message.equals(ConfigurationUtil.get("errorFlag"));
	}

	private void closeConnection() {
		isFinished = true;
		isNewCommunication = true;
	}

	private void openConnection() {
		secure();

		isNewCommunication = false;
		isFinished = false;

		int port = Integer.parseInt(ConfigurationUtil.get("chatServerPort1"));
		String address = ConfigurationUtil.get("chatServerAddress");
		try {
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			client = (SSLSocket) sf.createSocket(address, port);
			if (client.isConnected())
				System.out.println("Is connected");
			if (client.isClosed())
				System.out.println("Is closed");
			bufferedReader = StreamUtil.getReader(client);
			printWriter = StreamUtil.getWriter(client);
			new Thread(() -> {
				try {
					while (!isFinished)
						receiveMessage();
				} catch (IOException e) {
					closeConnection();
					ErrorHandler.handle(e);
				}
			}).start();
		} catch (UnknownHostException e) {
			ErrorHandler.handle(e);
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}

	private void secure() {
		System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);
	}
}
