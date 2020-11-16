package pro.artse.user.chat;

public interface IChatService extends IObserver {
	void sendMessage(String text);

	void receiveMessage();
}
