package pro.artse.medicalstaff.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.stream.events.StartDocument;

import com.sun.nio.file.SensitivityWatchEventModifier;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;

public class ChatService implements IChatService {

	private Socket client;
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
		// For queue
		int port = Integer.parseInt(ConfigurationUtil.get("chatServerPort2"));
		String address = ConfigurationUtil.get("chatServerAddress");

		try {
			client = new Socket(address, port);
			writer = StreamUtil.getWriter(client);
			reader = StreamUtil.getReader(client);
			isFinished = false;
			Thread receiveThread = new Thread(() -> {
				while (!isFinished) {
					try {
						receiveMessage();
					} catch (IOException e) {
						isFinished = true;
						e.printStackTrace();
						// TODO: handle exception
					}
				}
			});
			receiveThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String text) {
		writer.println(text);
	}

	@Override
	public void closeConnection() {
		// TODO: Fix error handling
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
			// TODO Handle exception
			e.printStackTrace();
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
}
