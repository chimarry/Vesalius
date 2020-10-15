package pro.artse.user.centralr.services;

public class ManagersFactory {
	public static IActivityLogService getActivityLogService() {
		return new ActivityLogService();
	}
}
