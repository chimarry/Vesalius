package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import pro.artse.centralr.api.ApiPaths;
import pro.artse.medicalstaff.centralr.services.IUserService;
import pro.artse.medicalstaff.centralr.services.ManagersFactory;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.models.CovidStatus;
import pro.artse.medicalstaff.models.KeyUserInfo;
import pro.artse.medicalstaff.util.RestApiUtil;

public class MedicalStaffMainController implements Initializable {

	private final IUserService userService = ManagersFactory.getUserService();

	@FXML
	private PieChart statisticChart;

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
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("CovidStatusName"));

		Task<MSResultMessage<KeyUserInfo[]>> task = new Task<MSResultMessage<KeyUserInfo[]>>() {
			@Override
			public MSResultMessage<KeyUserInfo[]> call() throws Exception {
				MSResultMessage<KeyUserInfo[]> data = userService.getAll();
				return data;
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<KeyUserInfo[]> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				usersTableView.getItems().setAll(resultMessage.getResult());
				usersTableView.getSelectionModel().selectedItemProperty()
						.addListener((obs, oldSelection, newSelection) -> {
							if (newSelection != null)
								disableOptions(false);
							else
								disableOptions(true);
						});
				setRowColor();
			} else
				MedicalStaffAlert.processResult(resultMessage);

		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
		});
		new Thread(task).start();

		// Add action handlers
		notInfectedCheckBox.setOnAction(this::chooseNotInfected);
		infectedCheckBox.setOnAction(this::chooseInfected);
		potInfectedCheckBox.setOnAction(this::choosePotInfected);

		searchButton.setOnAction(this::search);

		// Show statistics
		PieChart.Data infectedData = new PieChart.Data("Infected", 13);
		PieChart.Data notInfectedData = new PieChart.Data("Not infected", 25);
		PieChart.Data potInfectedData = new PieChart.Data("Potentially infected", 10);

		// TODO: Add statistics
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(infectedData, notInfectedData,
				potInfectedData);
		statisticChart.setData(pieChartData);
		// TODO: Enable chat
	}

	private void blockUser(ActionEvent event) {

	}

	private void search(ActionEvent event) {
		String searchFor = searchTokenTextField.getText();
		if (searchFor != null) {
			Task<MSResultMessage<KeyUserInfo>> task = new Task<MSResultMessage<KeyUserInfo>>() {
				@Override
				public MSResultMessage<KeyUserInfo> call() throws Exception {
					MSResultMessage<KeyUserInfo> data = userService.search(searchFor);
					return data;
				}
			};
			task.setOnSucceeded(e -> {
				MSResultMessage<KeyUserInfo> resultMessage = task.getValue();
				if (resultMessage.isSuccess()) {
					usersTableView.getItems().setAll(resultMessage.getResult());
					usersTableView.getSelectionModel().selectedItemProperty()
							.addListener((obs, oldSelection, newSelection) -> {
								if (newSelection != null)
									disableOptions(false);
								else
									disableOptions(true);
							});
					setRowColor();
				} else
					MedicalStaffAlert.processResult(resultMessage);

			});
			task.setOnFailed(e -> {
				MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
			});
			new Thread(task).start();
		}
	}

	private void chooseInfected(ActionEvent event) {
		if (infectedCheckBox.isSelected()) {
			potInfectedCheckBox.setSelected(false);
			notInfectedCheckBox.setSelected(false);
		}
		changeCovidStatus(CovidStatus.INFECTED);
	}

	private void chooseNotInfected(ActionEvent event) {
		if (notInfectedCheckBox.isSelected()) {
			potInfectedCheckBox.setSelected(false);
			infectedCheckBox.setSelected(false);
		}
		changeCovidStatus(CovidStatus.NOT_INFECTED);
	}

	private void choosePotInfected(ActionEvent event) {
		if (potInfectedCheckBox.isSelected()) {
			infectedCheckBox.setSelected(false);
			notInfectedCheckBox.setSelected(false);
		}
		changeCovidStatus(CovidStatus.POTENTIALLY_INFECTED);
	}

	private void changeCovidStatus(CovidStatus covidStatus) {

	}

	private void setRowColor() {
		usersTableView.setRowFactory(tv -> {
			TableRow<KeyUserInfo> row = new TableRow<>();
			BooleanBinding notInfected = row.itemProperty().isEqualTo(new KeyUserInfo("", 0));
			BooleanBinding infected = row.itemProperty().isEqualTo(new KeyUserInfo("", 1));
			row.styleProperty().bind(Bindings.when(infected).then("-fx-background-color: red ;").otherwise(
					Bindings.when(notInfected).then("-fx-background-color: lightgreen ;").otherwise("gray")));
			return row;
		});
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
