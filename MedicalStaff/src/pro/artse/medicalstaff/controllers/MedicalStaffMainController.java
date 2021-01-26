package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import pro.artse.medicalstaff.centralr.services.IUserService;
import pro.artse.medicalstaff.centralr.services.WebServiceFactory;
import pro.artse.medicalstaff.chat.IChatService;
import pro.artse.medicalstaff.chat.ISubscriber;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MSStatus;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.models.CovidStatus;
import pro.artse.medicalstaff.models.KeyUserInfo;
import pro.artse.medicalstaff.util.StageUtil;

public class MedicalStaffMainController implements Initializable, ISubscriber {

	private final IUserService userService = WebServiceFactory.getUserService();
	private final static IChatService chatService = WebServiceFactory.getChatService();

	private ObservableList<Node> standardUserMessagesData = FXCollections.<Node>observableArrayList();
	private ObservableList<Node> medicalStaffMessagesData = FXCollections.<Node>observableArrayList();

	@FXML
	private VBox standardUserMessages;

	@FXML
	private VBox medicalStaffMessages;

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
	private Button sendButton;

	@FXML
	private Tab medicalStaffTab;

	@FXML
	private Tab standardUserTab;

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

	public static void onClose(WindowEvent event) {
		chatService.closeConnection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize chat
		initializeChatSpace();
		chatService.register(this);
		chatService.openConnection();

		disableOptions(true);

		// Get users
		searchButton.setGraphic(new ImageView("file:../Design/search.png"));
		tokenColumn.setCellValueFactory(new PropertyValueFactory<>("Token"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("CovidStatusName"));

		getUsers();

		// Add action handlers
		notInfectedCheckBox.setOnAction(this::chooseNotInfected);
		infectedCheckBox.setOnAction(this::chooseInfected);
		potInfectedCheckBox.setOnAction(this::choosePotInfected);

		searchButton.setOnAction(this::search);
		blockUserButton.setOnAction(this::blockUser);

		sendButton.setOnAction(this::send);
		stopButton.setOnAction(this::stop);

		// Show statistics
		PieChart.Data infectedData = new PieChart.Data("Infected", 13);
		PieChart.Data notInfectedData = new PieChart.Data("Not infected", 25);
		PieChart.Data potInfectedData = new PieChart.Data("Potentially infected", 10);

		// TODO: Add statistics
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(infectedData, notInfectedData,
				potInfectedData);
		statisticChart.setData(pieChartData);
		// TODO: Enable chat

		documentsButton.setOnAction(this::viewDocuments);
	}

	@Override
	public void notify(String message) {
		Platform.runLater(() -> {
			TextArea textArea = new TextArea();
			textArea.setText("Standard user: " + message);
			standardUserMessagesData.add(textArea);
		});
	}

	private void initializeChatSpace() {
		Bindings.bindContentBidirectional(standardUserMessagesData, standardUserMessages.getChildren());
		Bindings.bindContentBidirectional(medicalStaffMessagesData, medicalStaffMessages.getChildren());
		addMessageSpace(standardUserMessagesData);
		addMessageSpace(medicalStaffMessagesData);
	}

	private void addMessageSpace(ObservableList<Node> list) {
		TextArea workingSpaceArea = new TextArea();
		workingSpaceArea.setMinHeight(20);
		list.add(workingSpaceArea);
		Separator workingSpaceSeparator = new Separator(Orientation.HORIZONTAL);
		workingSpaceArea.setMinHeight(3);
		list.add(workingSpaceSeparator);
	}

	private void send(ActionEvent event) {
		// TODO: Check which tab is selected
		String text = ((TextArea) standardUserMessagesData.get(0)).getText();
		TextArea sentMessageArea = new TextArea();
		sentMessageArea.setText("You[sent]: " + text);
		standardUserMessagesData.add(sentMessageArea);
		chatService.sendMessage(text);
	}

	private void stop(ActionEvent event) {
		// TODO: Check which tab is selected
		chatService.makeAvailable();
	}

	private void getUsers() {
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
	}

	private void blockUser(ActionEvent event) {
		KeyUserInfo userInfo = usersTableView.getSelectionModel().selectedItemProperty().get();
		Task<MSResultMessage<Boolean>> task = new Task<MSResultMessage<Boolean>>() {
			@Override
			public MSResultMessage<Boolean> call() throws Exception {
				MSResultMessage<Boolean> data = userService.blockUser(userInfo.getToken());
				return data;
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<Boolean> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				usersTableView.getItems().removeIf(user -> user.getToken().equals(userInfo.getToken()));
			} else
				MedicalStaffAlert.processResult(resultMessage);

		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
		});
		new Thread(task).start();
	}

	private void search(ActionEvent event) {
		String searchFor = searchTokenTextField.getText();
		if (searchFor != null && !searchFor.equals("")) {
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
				} else if (resultMessage.getStatus() == MSStatus.NOT_FOUND) {
					MedicalStaffAlert.processResult(resultMessage, AlertType.INFORMATION);
				} else
					MedicalStaffAlert.processResult(resultMessage, AlertType.ERROR);

			});
			task.setOnFailed(e -> {
				MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
			});
			new Thread(task).start();
		} else {
			getUsers();
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

	private void viewDocuments(ActionEvent event) {
		KeyUserInfo info = usersTableView.getSelectionModel().getSelectedItem();
		String token = info.getToken();
		StageUtil.switchStage(blockUserButton, "/pro/artse/medicalstaff/fxml/DocumentsForm.fxml", token);
	}
}
