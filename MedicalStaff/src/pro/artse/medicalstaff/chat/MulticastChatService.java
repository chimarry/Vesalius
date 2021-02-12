package pro.artse.medicalstaff.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.medicalstaff.errorhandling.ErrorHandler;

public class MulticastChatService implements IMulticastChatService {
	private static final int MAX_STAFF = 100000;
	private Integer id;
	private ISubscriber subscriber;
	private MulticastSocket socket;
	private InetAddress group;
	private int port;
	private boolean isFinished = false;

	@Override
	public void register(ISubscriber subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public void joinGroup() {
		try {
			port = Integer.parseInt(ConfigurationUtil.get("chatMulticastPort"));
			String address = ConfigurationUtil.get("chatMulticastAddress");
			socket = new MulticastSocket(port);
			group = InetAddress.getByName(address);
			socket.joinGroup(group);
			setId();
			receiveMessage();
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}

	@Override
	public void leaveGroup() {
		try {
			socket.leaveGroup(group);
			if (!socket.isClosed())
				socket.close();
			isFinished = true;
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}

	@Override
	public void sendMessage(String text) {
		String messageWithId = id + "#" + text;
		byte[] message = messageWithId.getBytes();
		DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			ErrorHandler.handle(e);
		}
	}

	@Override
	public void receiveMessage() {
		byte[] buffer = new byte[2048];
		Thread receiveThread = new Thread(() -> {
			while (!isFinished) {
				try {
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
					String[] messageParts = msg.split("#");
					if (!messageParts[0].equals(id.toString()))
						subscriber.notifyMedicalStaff(messageParts[1]);
				} catch (IOException e) {
					isFinished = true;
					ErrorHandler.handle(e);
				}
			}
		});
		receiveThread.start();
	}

	private void setId() {
		id = new Random().nextInt(MAX_STAFF);
	}
}
