package pro.arste.chat;

import java.net.Socket;

public interface IMedicalStaffManager {
	Socket getAvailable();
	void makeAvailable(Socket member);
}
