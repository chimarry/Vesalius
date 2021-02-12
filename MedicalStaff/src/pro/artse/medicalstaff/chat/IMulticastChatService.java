package pro.artse.medicalstaff.chat;

public interface IMulticastChatService extends IObserver {
	void joinGroup();

	void leaveGroup();

	void sendMessage(String text);

	void receiveMessage();
}
