package pro.artse.medicalstaff.chat;

public interface IChatService extends IObserver {
	void makeAvailable();

	void sendMessage(String text);

	void terminate();

	void receiveMessage();
}
