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

	@Override
	public void makeAvailable() {
		// For queue
		int port = Integer.parseInt(ConfigurationUtil.get("chatServerPort2"));
		String address = ConfigurationUtil.get("chatServerAddress");

		try {
			client = new Socket(address, port);
			writer = StreamUtil.getWriter(client);
			reader = StreamUtil.getReader(client);
			new Thread(() -> receiveMessage()).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(String text) {
		writer.println(text);
		writer.flush();
	}

	@Override
	public void terminate() {
		// TODO: Fix error handling
		// TODO: Fix this
		sendMessage(ConfigurationUtil.get("endFlag"));
		try {
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: Some optimization
		makeAvailable();
	}

	@Override
	public void receiveMessage() {
		String message;
		try {
			message = reader.readLine();
			subscriber.notify(message);
		} catch (IOException e) {
			// TODO Handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void register(ISubscriber subscriber) {
		this.subscriber = subscriber;
	}
}
