package pro.artse.user.controllers;

import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pro.artse.user.errorhandling.UserAlert;

/**
 * Startup application for Standard user application.
 * 
 * @author Marija
 *
 */
public class StartupApplication extends Application {

	public static boolean isRegistered = false;

	/**
	 * Shows form for registration or login, depending on user' status.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(UserAlert::showExceptionError);

		Pane mainPane = null;
		// TODO: Change to true
		if (Preferences.userRoot().get("token", null) != null)
			isRegistered = false;
		if (!isRegistered)
			mainPane = (GridPane) FXMLLoader.load(getClass().getResource("/pro/artse/user/fxml/RegisterForm.fxml"));
		else
			mainPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/pro/artse/user/fxml/LoginForm.fxml"));

		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
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