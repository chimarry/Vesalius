package pro.artse.user.errorhandling;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Encapsulates behavior for notifying user about the actions.
 * 
 * @author Marija
 *
 */
public final class UserAlert {

	public static final String REQUIRED_FIELDS = "All fields are required!";
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
		e.printStackTrace();
	}

	public static <T> void processResult(SUResultMessage<T> resultMessage) {
		if (resultMessage.isSuccess())
			UserAlert.alert(AlertType.INFORMATION, resultMessage.getStatus().toString(), resultMessage.getMessage());
		else
			UserAlert.alert(AlertType.ERROR, resultMessage.getStatus().toString(), resultMessage.getMessage());
	}
}
