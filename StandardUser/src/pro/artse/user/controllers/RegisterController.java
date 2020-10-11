package pro.artse.user.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.artse.tokenserver.services.TokenService;
import pro.artse.user.factories.WebServiceFactory;
import pro.artse.user.util.StageUtil;
import pro.artse.user.util.UserAlert;
import pro.artse.user.util.Validator;

public class RegisterController implements Initializable {

	private final TokenService tokenService = WebServiceFactory.getTokenService();

	@FXML
	private Button registerButton;

	@FXML
	private TextArea lastNameBox;

	@FXML
	private TextArea firstNameBox;

	@FXML
	private TextArea ubnBox;

	@FXML
	private GridPane registerPane;

	/**
	 * Creates register controller.
	 * 
	 * @throws ServiceException
	 */
	public RegisterController() throws ServiceException {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		registerButton.setOnAction(this::register);
	}

	/**
	 * Implements registration process with error handling.
	 * 
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	private void register(ActionEvent action) {
		if (!areValidFields()) {
			UserAlert.alert(AlertType.ERROR, UserAlert.REQUIRED_FIELDS);
		} else {
			String jsonResultString;
			try {
				jsonResultString = tokenService.generateToken(firstNameBox.getText(), lastNameBox.getText(),
						ubnBox.getText());
				ResultMessage<String> token = new Gson().fromJson(jsonResultString,
						new ResultMessage<String>(null).getClass());
				Preferences userPreferences = Preferences.userRoot();
				userPreferences.put("token", token.getResult());
				if (token.isSuccess()) {
					StageUtil.switchStage(registerButton, "/pro/artse/user/fxml/LoginDialog.fxml");
				} else
					UserAlert.alert(AlertType.ERROR, token.getOperationStatus().toString(),
							"Registration failed." + token.getMessage());
			} catch (RemoteException e) {
				UserAlert.alert(AlertType.ERROR, "Unable to connect to server.");
			}
		}
	}

	/**
	 * Check if field are valid.
	 * 
	 * @return True if are valid, false if not.
	 */
	private boolean areValidFields() {
		return !Validator.AreNullOrEmpty(firstNameBox.getText(), lastNameBox.getText(), ubnBox.getText());
	}
}
