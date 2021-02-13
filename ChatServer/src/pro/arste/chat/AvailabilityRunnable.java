package pro.arste.chat;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import pro.arste.chat.errorhandling.ErrorHandler;
import pro.artse.chat.util.ConfigurationUtil;

public class AvailabilityRunnable implements Runnable {
	private final IMedicalStaffManager medicalStaffManager = DependencyInjector.getMedicalStaffManager();

	@Override
	public void run() {
		InetSocketAddress socketAddress = ConfigurationUtil.getInetSocketAddress("chatServerPort2",
				"chatServerAddress");
		SSLServerSocketFactory sslfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try (ServerSocket ss = sslfactory.createServerSocket()) {
			ss.bind(socketAddress);
			while (true) {
				SSLSocket medicalStaffMember = (SSLSocket) ss.accept();
				medicalStaffManager.makeAvailable(medicalStaffMember);
			}
		} catch (Exception ex) {
			ErrorHandler.handle(ex);
		}
	}
}
