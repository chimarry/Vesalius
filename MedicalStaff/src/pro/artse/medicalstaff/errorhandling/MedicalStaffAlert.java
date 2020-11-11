package pro.artse.medicalstaff.errorhandling;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class MedicalStaffAlert {

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