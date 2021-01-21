package pro.artse.user.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.factories.ManagersFactory;
import pro.artse.user.managers.ILoginManager;
import pro.artse.user.util.StageUtil;

public class ChangePasswordController implements Initializable {

	private final ILoginManager loginManager = ManagersFactory.getLoginManager();

	@FXML
	private Button changePasswordButton;

	@FXML
	private Button cancelButton;

	@FXML
	private PasswordField oldPasswordField;

	@FXML
	private PasswordField newPasswordField;

	@FXML
	private PasswordField repeatPasswordField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		changePasswordButton.setOnAction(this::changePassword);
		cancelButton.setOnAction(this::cancel);
	}

	private void cancel(ActionEvent event) {
		Stage currentStage = (Stage) cancelButton.getParent().getScene().getWindow();
		currentStage.close();
	}
	
	private void changePassword(ActionEvent event) {
		if (!areValidPasswords()) {
			UserAlert.alert(AlertType.ERROR, "Data is invalid.");
			repeatPasswordField.setText("");
			newPasswordField.setText("");
			oldPasswordField.setText("");
		} else {
			boolean isPasswordChanged = loginManager.changePassword(oldPasswordField.getText(), newPasswordField.getText());
			if (isPasswordChanged) {
				Stage currentStage = (Stage) changePasswordButton.getParent().getScene().getWindow();
				currentStage.close();
			} else
				UserAlert.alert(AlertType.ERROR, "Password is not changed");
		}
	}
	
	private boolean areValidPasswords() {
		return !Validator.AreNullOrEmpty(oldPasswordField.getText(),newPasswordField.getText(), repeatPasswordField.getText())
				&& newPasswordField.getText().equals(repeatPasswordField.getText());
	}
}
