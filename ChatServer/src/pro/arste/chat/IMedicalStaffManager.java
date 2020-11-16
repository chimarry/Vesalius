package pro.arste.chat;

import java.net.Socket;

import pro.artse.chat.models.MedicalStaffMember;

public interface IMedicalStaffManager {
	Socket getAvailable();
	void makeAvailable(Socket member);
}
