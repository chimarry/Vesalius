package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JComboBox.KeySelectionManager;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import pro.artse.medicalstaff.centralr.services.IUserService;
import pro.artse.medicalstaff.centralr.services.ManagersFactory;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.models.CovidStatus;
import pro.artse.medicalstaff.models.KeyUserInfo;

public class MedicalStaffMainController implements Initializable {

	private final IUserService userService = ManagersFactory.getUserService();

	@FXML
	private Button searchButton;

	@FXML
	private Button locationsButton;

	@FXML
	private Button documentsButton;

	@FXML
	private Button blockUserButton;

	@FXML
	private Button stopButton;

	@FXML
	private Tab medicalStaffTab;

	@FXML
	private Tab standardUserTab;

	@FXML
	private TextArea standardUserTextArea;

	@FXML
	private TextArea medicalStaffTextArea;

	@FXML
	private TableView<KeyUserInfo> usersTableView;

	@FXML
	private TableColumn<KeyUserInfo, String> tokenColumn;

	@FXML
	private TableColumn<KeyUserInfo, CovidStatus> statusColumn;

	@FXML
	private CheckBox infectedCheckBox;

	@FXML
	private CheckBox potInfectedCheckBox;

	@FXML
	private CheckBox notInfectedCheckBox;

	@FXML
	private TextField searchTokenTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableOptions(true);

		// Get users
		searchButton.setGraphic(new ImageView("file:../Design/search.png"));
		tokenColumn.setCellValueFactory(new PropertyValueFactory<>("Token"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("CovidStatus"));

		Task<MSResultMessage<KeyUserInfo[]>> task = new Task<MSResultMessage<KeyUserInfo[]>>() {
			@Override
			public MSResultMessage<KeyUserInfo[]> call() throws Exception {
				MSResultMessage<KeyUserInfo[]> data = userService.getAll();
				System.out.println(data.getStatus());
				return data;
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<KeyUserInfo[]> resultMessage = task.getValue();
			if (resultMessage.isSuccess())
				usersTableView.getItems().setAll(resultMessage.getResult());
			else
				MedicalStaffAlert.processResult(resultMessage);
		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
		});
		new Thread(task).start();

		usersTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null)
				disableOptions(false);
			else
				disableOptions(true);
		});
		notInfectedCheckBox.setOnAction(this::chooseNotInfected);
		infectedCheckBox.setOnAction(this::chooseInfected);
		potInfectedCheckBox.setOnAction(this::choosePotInfected);
		// TODO: Implement search mechanism
		// TODO: Enable chat
	}

	private void chooseInfected(ActionEvent event) {
		if (infectedCheckBox.isSelected()) {
			potInfectedCheckBox.setSelected(false);
			notInfectedCheckBox.setSelected(false);
		}
		changeCovidStatus();
	}

	private void chooseNotInfected(ActionEvent event) {
		if (notInfectedCheckBox.isSelected()) {
			potInfectedCheckBox.setSelected(false);
			infectedCheckBox.setSelected(false);
		}
		changeCovidStatus();
	}

	private void choosePotInfected(ActionEvent event) {
		if (potInfectedCheckBox.isSelected()) {
			infectedCheckBox.setSelected(false);
			notInfectedCheckBox.setSelected(false);
		}
		changeCovidStatus();
	}

	private void changeCovidStatus() {
		// TODO: Change user's covid status
		if (notInfectedCheckBox.isSelected()) {
			infectedCheckBox.setSelected(false);
			potInfectedCheckBox.setSelected(false);
		} else if (infectedCheckBox.isSelected()) {

		} else if (potInfectedCheckBox.isSelected()) {
			notInfectedCheckBox.setSelected(false);
			infectedCheckBox.setSelected(false);
		}
	}

	private void disableOptions(boolean isDisabled) {
		locationsButton.setDisable(isDisabled);
		blockUserButton.setDisable(isDisabled);
		documentsButton.setDisable(isDisabled);
		infectedCheckBox.setDisable(isDisabled);
		potInfectedCheckBox.setDisable(isDisabled);
		notInfectedCheckBox.setDisable(isDisabled);
	}
}
