package pro.arste.chat;

public class DependencyInjector {
	public static IMedicalStaffManager getMedicalStaffManager() {
		return new MedicalStaffManager();
	}
}
