package pro.arste.chat;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import pro.artse.chat.util.ConfigurationUtil;

public class UnicastChatConnectionRunnable implements Runnable {
	private final IMedicalStaffManager medicalStaffManager = DependencyInjector.getMedicalStaffManager();

	@Override
	public void run() {
		InetSocketAddress serverChannel = ConfigurationUtil.getInetSocketAddress("chatServerPort1",
				"chatServerAddress");
		try (ServerSocket ss = new ServerSocket()) {
			ss.bind(serverChannel);
			System.out.println("Accepting standard user on port:" + serverChannel.getPort() + " " + LocalDate.now());
			while (true) {
				Socket standardUser = ss.accept();
				Socket medicalStaffMember = medicalStaffManager.getAvailable();
				System.out.println("SU: " + standardUser.getPort() + " MF" + medicalStaffMember.getPort());
				new Thread(new UnicastChatRunnable(standardUser, medicalStaffMember)).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
