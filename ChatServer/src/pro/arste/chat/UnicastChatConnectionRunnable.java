package pro.arste.chat;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import pro.arste.chat.errorhandling.ErrorHandler;
import pro.artse.chat.util.ConfigurationUtil;

public class UnicastChatConnectionRunnable implements Runnable {
	private final IMedicalStaffManager medicalStaffManager = DependencyInjector.getMedicalStaffManager();

	@Override
	public void run() {
		InetSocketAddress serverChannel = ConfigurationUtil.getInetSocketAddress("chatServerPort1",
				"chatServerAddress");
		SSLServerSocketFactory sslfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try (ServerSocket ss = sslfactory.createServerSocket()) {
			ss.bind(serverChannel);
			while (true) {
				SSLSocket standardUser = (SSLSocket) ss.accept();
				System.out.println(standardUser.getLocalPort() + "su");
				SSLSocket medicalStaffMember = medicalStaffManager.getAvailable();
				System.out.println(medicalStaffMember.getLocalPort() + "ms");
				new Thread(new UnicastChatRunnable(standardUser, medicalStaffMember)).start();
			}
		} catch (Exception ex) {
			ErrorHandler.handle(ex);
		}
	}
}
