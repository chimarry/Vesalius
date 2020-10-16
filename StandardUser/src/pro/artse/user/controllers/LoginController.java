package pro.artse.user.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import pro.artse.user.util.StageUtil;
import pro.artse.user.util.UserAlert;
import pro.artse.user.util.Validator;

/**
 * Interacts with login form view.
 * 
 * @author Marija
 *
 */
public class LoginController implements Initializable {

	@FXML
	private PasswordField passwordBox;

	@FXML
	private Button loginButton;

	@FXML
	private Label tokenDisplay;

	@FXML
	private AnchorPane loginPane;

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
			Preferences.userRoot().put("logInAt", LocalDateTime.now().toString());
			StageUtil.switchStage(loginButton, "/pro/artse/user/fxml/StandardUserMainForm.fxml");
		}
	}
}
