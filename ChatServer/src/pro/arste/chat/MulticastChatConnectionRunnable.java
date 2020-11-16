package pro.arste.chat;

import pro.artse.chat.util.ConfigurationUtil;

public class MulticastChatConnectionRunnable implements Runnable {

	@Override
	public void run() {
		String address = ConfigurationUtil.get("chatMulticastAddress");
		int port = Integer.parseInt(ConfigurationUtil.get("chatMutlicastPort"));
		// TODO: Add logic
	}
}
