package pro.artse.medicalstaff.controllers;

import pro.artse.medicalstaff.managers.*;

public class ManagersFactory {
	public static IFileServerManager getFileServerManager() {
		return new FileServerManager();
	}
}
