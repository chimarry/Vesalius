package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JComboBox.KeySelectionManager;

import javafx.concurrent.Task;
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
		// TODO: Get users
		// TODO: Implement search mechanism
		// TODO: When user is clicked, enable options
		// TODO: Enable chat
	}
}
