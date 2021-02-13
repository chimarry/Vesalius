package pro.artse.medicalstaff.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;
import pro.artse.medicalstaff.errorhandling.ErrorHandler;

public class ChatService implements IChatService {
	private static final String TRUST_STORE_PATH = "C:\\Users\\Vasic\\Desktop\\keystore.jks";
	private static final String TRUST_STORE_PASSWORD = "securemdp";

	private SSLSocket client;
	private BufferedReader reader;
	private PrintWriter writer;

	private ISubscriber subscriber;

	private Boolean isFinished = false;

	@Override
	public void makeAvailable() {
		sendMessage(ConfigurationUtil.get("endFlag"));
	}

	@Override
	public void openConnection() {
		secure();

		int port = Integer.parseInt(ConfigurationUtil.get("chatServerPort2"));
		String address = ConfigurationUtil.get("chatServerAddress");

		try {
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			client = (SSLSocket) sf.createSocket(address, port);
			if (client.isConnected())
				System.out.println("Is connected");
			if (client.isClosed())
				System.out.println("Is closed");
			writer = StreamUtil.getWriter(client);
			reader = StreamUtil.getReader(client);
			isFinished = false;
			Thread receiveThread = new Thread(() -> {
				while (!isFinished) {
					try {
						receiveMessage();
					} catch (IOException e) {
						isFinished = true;
						ErrorHandler.handle(e);
					}
				}
			});
			receiveThread.start();
		} catch (UnknownHostException e) {
			ErrorHandler.handle(e);
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}

	@Override
	public void sendMessage(String text) {
		writer.println(text);
	}

	@Override
	public void closeConnection() {
		// TODO: Fix this
		isFinished = true;
	}

	@Override
	public void receiveMessage() throws IOException {
		String message;
		try {
			message = reader.readLine();
			System.out.println(message);
			if (isFlag(message)) {
				writer.println(message);
			} else if (message.equals(ConfigurationUtil.get("closeFlag"))) {
				closeConnection();
				openConnection();
			} else
				subscriber.notify(message);
		} catch (IOException e) {
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

	private void secure() {
		System.setProperty("javax.net.ssl.trustStore", TRUST_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASSWORD);
	}
}
