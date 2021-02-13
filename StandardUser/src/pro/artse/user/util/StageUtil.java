package pro.artse.user.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pro.artse.user.errorhandling.UserAlert;

/**
 * Manipulates scenes and stages.
 * 
 * @author Marija
 *
 */
public final class StageUtil {

	/**
	 * Switches stage. Closes current, and opens new stage.
	 * 
	 * @param currentPane Main element of the scene that needs to be closed.
	 * @param resource    Path to new view.
	 */
	public static void switchStage(Node currentPane, String resource) {
		try {
			Stage currentStage = (Stage) currentPane.getParent().getScene().getWindow();
			currentStage.close();
			Pane pane = (Pane) FXMLLoader.load(StageUtil.class.getResource(resource));
			Stage newStage = new Stage();
			newStage.setScene(new Scene(pane));
			newStage.getIcons().add(new Image("file:../Design/virusIcon.png"));
			newStage.show();
		} catch (Exception e) {
			UserAlert.alert(AlertType.ERROR, "Unable to open new window");
		}
	}

	/**
	 * Closes dialog.
	 * 
	 * @param currentNode Some element of the scene that needs to be closed.
	 */
	public static void closeDialog(Node currentNode) {
		Stage currentStage = (Stage) currentNode.getParent().getScene().getWindow();
		currentStage.close();
	}

	/**
	 * Shows dialog.
	 * 
	 * @param resource Path to the dialog.
	 */
	public static void showDialog(String resource) {
		try {
			Pane pane = (Pane) FXMLLoader.load(StageUtil.class.getResource(resource));
			Stage newStage = new Stage();
			newStage.setScene(new Scene(pane));
			newStage.getIcons().add(new Image("file:../Design/virusIcon.png"));
			newStage.show();
		} catch (Exception e) {
			UserAlert.alert(AlertType.ERROR, "Unable to open new window");
		}
	}
}
