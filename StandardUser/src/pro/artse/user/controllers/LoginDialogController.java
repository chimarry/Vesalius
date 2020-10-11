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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.annotations.Ignore;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import pro.artse.user.util.StageUtil;
import pro.artse.user.util.UserAlert;
import pro.artse.user.util.Validator;

/**
 * Interaction with login dialog window.
 * 
 * @author Marija
 *
 */
public class LoginDialogController implements Initializable {

	@FXML
	private Button loginButton;

	@FXML
	private Button cancelButton;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField repeatPasswordField;

	@FXML
	private AnchorPane loginPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setOnAction(this::login);
		cancelButton.setOnAction(this::cancel);
		UserAlert.alert(AlertType.CONFIRMATION, Preferences.userRoot().get("token", "No token"));
	}

	/**
	 * Defines behavior for login.
	 * 
	 * @param event
	 */
	private void login(ActionEvent event) {
		if (!areValidPasswords())
			UserAlert.alert(AlertType.ERROR, "Passwords do not match.");
		else {
			Preferences.userRoot().put("password", passwordField.getText());
			StageUtil.switchStage(loginButton, "/pro/artse/user/fxml/StandardUserMainForm.fxml");
		}
	}

	/**
	 * Closes current window.
	 * 
	 * @param event
	 */
	private void cancel(ActionEvent event) {

	}

	private boolean areValidPasswords() {
		return !Validator.AreNullOrEmpty(passwordField.getText(), repeatPasswordField.getText())
				&& passwordField.getText().equals(repeatPasswordField.getText());
	}
}
