package pro.artse.user.chat;

import java.io.IOException;

public interface IChatService extends IObserver {
	void sendMessage(String text);

	void receiveMessage() throws IOException;
}
