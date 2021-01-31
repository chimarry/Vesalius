package pro.artse.medicalstaff.centralr.services;

import pro.artse.medicalstaff.chat.*;

public final class WebServiceFactory {
	public static IUserService getUserService() {
		return new UserService();
	}

	public static IChatService getChatService() {
		return new ChatService();
	}

	public static ILocationService getLocationService() {
		return new LocationService();
	}
}
