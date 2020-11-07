package pro.artse.medicalstaff.centralr.services;

public final class ManagersFactory {
	public static IUserService getUserService() {
		return new UserService();
	}
}
