package pro.artse.medicalstaff.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;

public final class StageUtil {

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
	 * Switches stage. Closes current, and opens new stage.
	 * 
	 * @param currentPane Main element of the scene that needs to be closed.
	 * @param resource    Path to new view.
	 */
	public static void showDialog(String resource, String token) {
		try {
			FXMLLoader loader = new FXMLLoader(StageUtil.class.getResource(resource));
			Pane pane = (Pane) loader.load();
			ITokenSetup controller = (ITokenSetup) loader.getController();
			controller.setToken(token);
			Stage newStage = new Stage();
			newStage.setScene(new Scene(pane));
			newStage.show();
		} catch (Exception e) {
			MedicalStaffAlert.alert(AlertType.ERROR, "Unable to open new window");
		}
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
			MedicalStaffAlert.alert(AlertType.ERROR, "Unable to open new window");
		}
	}
}
