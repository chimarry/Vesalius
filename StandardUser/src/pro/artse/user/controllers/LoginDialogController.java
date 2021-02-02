package pro.artse.user.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.factories.ManagersFactory;
import pro.artse.user.managers.ILoginManager;
import pro.artse.user.models.User;
import pro.artse.user.util.StageUtil;

/**
 * Interaction with login dialog window.
 * 
 * @author Marija
 *
 */
public class LoginDialogController implements Initializable {

	private final ILoginManager loginManager = ManagersFactory.getLoginManager();

	@FXML
	private Button loginButton;

	@FXML
	private Button cancelButton;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField repeatPasswordField;

	@FXML
	private TextField tokenField;

	@FXML
	private AnchorPane loginPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setOnAction(this::login);
		cancelButton.setOnAction(this::cancel);
	}

	/**
	 * Defines behavior for login.
	 * 
	 * @param event
	 */
	private void login(ActionEvent event) {
		if (!areValidPasswords() || Validator.isNullOrEmpty(tokenField.getText())) {
			UserAlert.alert(AlertType.ERROR, "Data is invalid.");
			repeatPasswordField.clear();
			passwordField.clear();
		} else if (tokenField.getText().equals(User.getInstance().getToken())) {
			UserAlert.alert(AlertType.ERROR, "Token is not valid.");
			repeatPasswordField.clear();
			tokenField.clear();
			passwordField.clear();
		} else {
			boolean isUserSaved = loginManager.saveUser(tokenField.getText(), passwordField.getText());
			if (isUserSaved) {
				StageUtil.switchStage(loginButton, "/pro/artse/user/fxml/StandardUserMainForm.fxml");
			} else
				UserAlert.alert(AlertType.ERROR, "Credentials are not saved");
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
		return !Validator.areNullOrEmpty(passwordField.getText(), repeatPasswordField.getText())
				&& passwordField.getText().equals(repeatPasswordField.getText());
	}
}
