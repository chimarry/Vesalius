package pro.artse.user.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import pro.artse.user.util.UserAlert;

public class LoginDialogController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserAlert.alert(AlertType.CONFIRMATION, RegisterController.USER_TOKEN);
	}

}
