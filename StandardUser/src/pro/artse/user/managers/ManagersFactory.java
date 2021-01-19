package pro.artse.user.managers;

public class ManagersFactory {
	public static ILoginManager getLoginManager() {
		return new LoginManager();
	}
}
