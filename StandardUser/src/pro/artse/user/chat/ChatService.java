package pro.artse.user.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;

public class ChatService implements IChatService {

	private Boolean isFinished = false;
	private Boolean isNewCommunication = true;

	private Socket client;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;

	private ISubscriber subscriber;

	@Override
	public void sendMessage(String text) {
		if (isNewCommunication || client.isClosed()) {
			openConnection();
			System.out.println("Connection opened: " + client);
		}
		if (!isFinished) {
			printWriter.println(text);
			printWriter.flush();
		}
	}

	@Override
	public void receiveMessage() {
		String message;
		try {
			message = bufferedReader.readLine();
			if (message.equals(ConfigurationUtil.get("endFlag"))) {
				closeConnection();
				subscriber.notify("");
			}
			subscriber.notify(message);
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		isFinished = true;
		isNewCommunication = true;
		// TODO: Fix error handling
		try {
			bufferedReader.close();
			printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openConnection() {
		isNewCommunication = false;
		isFinished = false;
		// TODO: Fix error handling
		int sendPort = Integer.parseInt(ConfigurationUtil.get("chatServerPort1"));
		String address = ConfigurationUtil.get("chatServerAddress");
		try {
			client = new Socket(address, sendPort);
			bufferedReader = StreamUtil.getReader(client);
			printWriter = StreamUtil.getWriter(client);
			new Thread(() -> receiveMessage()).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void register(ISubscriber subscriber) {
		this.subscriber = subscriber;
	}
}
