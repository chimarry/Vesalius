package pro.artse.medicalstaff.errorhandling;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class MedicalStaffAlert {

	public static final String REMOTE_CONNECTION_PROBLEM = "There has been a problem with connection to remote server";
	public static final String CENTRAL_REGISTER_CONNECTION_PROBLEM = "Connection with central register could not been established.";

	/**
	 * Alerts user about actions.
	 * 
	 * @param type    Level of alert. @see AlertType
	 * @param message Message to display.
	 */
	public static void alert(AlertType type, String message) {
		alert(type, type.toString(), message);
	}

	/**
	 * Alerts user about actions.
	 * 
	 * @param type    Level of alert. @see AlertType
	 * @param title   Title of alert.
	 * @param message Message to display.
	 */
	public static void alert(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}

	public static void showExceptionError(Thread t, Throwable e) {
		alert(AlertType.ERROR, "Exception occured.");
	}

	public static <T> void processResult(MSResultMessage<T> resultMessage) {

		if (resultMessage.isSuccess())
			processResult(resultMessage, AlertType.INFORMATION);
		else
			processResult(resultMessage, AlertType.ERROR);
	}

	public static <T> void processResult(MSResultMessage<T> resultMessage, AlertType type) {
		MedicalStaffAlert.alert(type, resultMessage.getStatus().toString(), resultMessage.getMessage());
	}
}