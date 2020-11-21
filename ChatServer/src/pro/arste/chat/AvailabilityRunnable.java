package pro.arste.chat;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import pro.artse.chat.util.ConfigurationUtil;

public class AvailabilityRunnable implements Runnable {
	private final IMedicalStaffManager medicalStaffManager = DependencyInjector.getMedicalStaffManager();

	@Override
	public void run() {
		InetSocketAddress socketAddress = ConfigurationUtil.getInetSocketAddress("chatServerPort2",
				"chatServerAddress");
		try (ServerSocket ss = new ServerSocket()) {
			ss.bind(socketAddress);
			while (true) {
				Socket medicalStaffMember = ss.accept();
				System.out.println(
						"Accepting medical staff on port:" + medicalStaffMember.getPort() + " " + LocalDate.now());
				// Save socket connection
				medicalStaffManager.makeAvailable(medicalStaffMember);
			}
		} catch (Exception ex) {
			// TODO: Add error handler: BindException
			ex.printStackTrace();
		}
	}
}
