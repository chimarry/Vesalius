package pro.artse.medicalstaff.chat;

import java.io.IOException;

public interface IChatService extends IObserver {
	void openConnection();
	
	void makeAvailable();

	void sendMessage(String text);

	void closeConnection();
	
	void receiveMessage() throws IOException;
}
