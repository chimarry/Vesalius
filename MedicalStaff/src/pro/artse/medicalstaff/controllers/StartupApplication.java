package pro.artse.medicalstaff.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartupApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane mainPane = (GridPane) FXMLLoader
				.load(getClass().getResource("/pro/artse/medicalstaff/fxml/MedicalStaffMainForm.fxml"));
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.setOnHidden(MedicalStaffMainController::onClose);
		primaryStage.getIcons().add(new Image("file:../Design/virusIcon.png"));
		primaryStage.show();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application. main()
	 * serves only as fallback in case the application can not be launched through
	 * deployment artifacts, e.g., in IDEs with limited FX support. NetBeans ignores
	 * main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
