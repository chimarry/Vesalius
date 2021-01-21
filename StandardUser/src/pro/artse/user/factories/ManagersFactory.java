package pro.artse.user.factories;

import pro.artse.user.managers.ILoginManager;
import pro.artse.user.managers.LoginManager;

public class ManagersFactory {
	public static ILoginManager getLoginManager() {
		return new LoginManager();
	}
}
