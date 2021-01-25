package pro.artse.user.factories;

import pro.artse.user.managers.*;

public class ManagersFactory {
	public static ILoginManager getLoginManager() {
		return new LoginManager();
	}

	public static IFileServerManager getFileServerManager() {
		return new FileServerManager();
	}
}
