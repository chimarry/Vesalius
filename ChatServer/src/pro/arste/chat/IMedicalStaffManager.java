package pro.arste.chat;

import java.net.Socket;

import javax.net.ssl.SSLSocket;

public interface IMedicalStaffManager {
	SSLSocket getAvailable();
	void makeAvailable(SSLSocket member);
}
