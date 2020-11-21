package pro.artse.medicalstaff.chat;

public interface IChatService extends IObserver {
	void openConnection();
	
	void makeAvailable();

	void sendMessage(String text);

	void closeConnection();
	
	void receiveMessage();
}
