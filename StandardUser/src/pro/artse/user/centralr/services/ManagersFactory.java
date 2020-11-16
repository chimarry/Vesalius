package pro.artse.user.centralr.services;

import pro.artse.user.chat.*;

public class ManagersFactory {
	public static IActivityLogService getActivityLogService() {
		return new ActivityLogService();
	}

	public static IChatService getChatService() {
		return new ChatService();
	}
}
