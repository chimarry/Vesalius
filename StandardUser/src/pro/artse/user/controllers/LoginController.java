package pro.artse.user.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pro.artse.user.util.UserAlert;
import pro.artse.user.util.Validator;

public class LoginController implements Initializable {

	@FXML
	private PasswordField passwordBox;

	@FXML
	private Button loginButton;

	@FXML
	private Label tokenDisplay;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setOnAction(this::login);
		tokenDisplay.setText(Preferences.userRoot().get("token", "No related token"));
	}

	/**
	 * Defines behavior for login.
	 * 
	 * @param event
	 */
	private void login(ActionEvent event) {
		if (Validator.IsNullOrEmpty(passwordBox.getText()))
			UserAlert.alert(AlertType.ERROR, UserAlert.REQUIRED_FIELDS);
		else if (!Preferences.userRoot().get("password", "").equals(passwordBox.getText()))
			UserAlert.alert(AlertType.ERROR, "Password is invalid.");
		else {
			GridPane pane;
			try {
				pane = (GridPane) FXMLLoader
						.load(getClass().getResource("/pro/artse/user/fxml/StandardUserMainForm.fxml"));
				Stage stage = new Stage();
				stage.setScene(new Scene(pane));
				stage.show();
			} catch (IOException e) {
				UserAlert.alert(AlertType.ERROR, "Could not show main form!");
			}
		}
	}
}
