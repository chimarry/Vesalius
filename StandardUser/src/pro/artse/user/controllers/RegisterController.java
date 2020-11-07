package pro.artse.user.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.util.Mapper;
import pro.artse.tokenserver.services.TokenService;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.factories.WebServiceFactory;
import pro.artse.user.util.StageUtil;

/**
 * Responsible for interaction with registration form.
 * 
 * @author Marija
 *
 */
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
				SUResultMessage<String> token = pro.artse.user.util.Mapper.mapFromTs(jsonResultString, String.class);
				// TODO: Save token
				// Preferences userPreferences = Preferences.userRoot();
				// userPreferences.put("token", token.getResult());
				if (token.isSuccess()) {
					StageUtil.switchStage(registerButton, "/pro/artse/user/fxml/LoginDialog.fxml");
				} else
					UserAlert.alert(AlertType.ERROR, token.getStatus().toString(),
							"Registration failed." + token.getMessage());
			} catch (RemoteException e) {
				// TODO: Add logger
				e.printStackTrace();
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
