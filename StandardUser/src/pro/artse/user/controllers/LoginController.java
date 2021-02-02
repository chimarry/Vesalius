package pro.artse.user.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.factories.ManagersFactory;
import pro.artse.user.managers.ILoginManager;
import pro.artse.user.util.StageUtil;

/**
 * Interacts with login form view.
 * 
 * @author Marija
 *
 */
public class LoginController implements Initializable {

	private final ILoginManager loginManager = ManagersFactory.getLoginManager();

	@FXML
	private PasswordField passwordBox;

	@FXML
	private Button loginButton;

	@FXML
	private TextField tokenField;

	@FXML
	private AnchorPane loginPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.setOnAction(this::login);
	}

	/**
	 * Defines behavior for login.
	 * 
	 * @param event
	 */
	private void login(ActionEvent event) {
		if (Validator.areNullOrEmpty(passwordBox.getText(), tokenField.getText()))
			UserAlert.alert(AlertType.ERROR, UserAlert.REQUIRED_FIELDS);
		else {
			boolean loggedIn = loginManager.login(tokenField.getText(), passwordBox.getText());
			if (loggedIn)
				StageUtil.switchStage(loginButton, "/pro/artse/user/fxml/StandardUserMainForm.fxml");
			else {
				UserAlert.alert(AlertType.ERROR, "Password or token is invalid.");
				passwordBox.setText("");
				tokenField.setText("");
			}
		}
	}
}
