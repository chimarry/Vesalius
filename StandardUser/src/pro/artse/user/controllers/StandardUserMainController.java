package pro.artse.user.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.XYZParam;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import pro.artse.user.centralr.services.IActivityLogService;
import pro.artse.user.centralr.services.ILocationService;
import pro.artse.user.centralr.services.ManagersFactory;
import pro.artse.user.chat.IChatService;
import pro.artse.user.chat.ISubscriber;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.models.Location;
import pro.artse.user.models.User;
import pro.artse.user.util.StageUtil;

public class StandardUserMainController implements Initializable, ISubscriber {
	private final IActivityLogService activityService = ManagersFactory.getActivityLogService();
	private final IChatService chatService = ManagersFactory.getChatService();
	private final ILocationService locationService = ManagersFactory.getLocationService();

	private ObservableList<Node> medicalStaffMessagesData = FXCollections.<Node>observableArrayList();

	@FXML
	private MapView mapView;

	@FXML
	private MenuBar mainMenu;

	@FXML
	private Button sendMsgButton;

	@FXML
	private VBox medicalStaffMessages;

	@FXML
	private Button uploadDocsButton;

	@FXML
	private Button viewDocsButton;

	@FXML
	private Button sendLocationButton;

	@FXML
	private TextArea longitudeBox;

	@FXML
	private TextArea latitudeBox;

	@FXML
	private DatePicker sinceDatePicker;

	@FXML
	private DatePicker untilDatePicker;

	@FXML
	private TextField sinceTimeTextField;

	@FXML
	private TextField untilTimeTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chatService.register(this);
		initializeChatSpace();

		Menu logoutMenu = new Menu("Log out");
		logoutMenu.setGraphic(new ImageView("file:../Design/logout.png"));
		logoutMenu.setStyle("-fx-accent: #a67a53");
		MenuItem logoutItem = new MenuItem("Log out");
		logoutItem.setOnAction(this::logout);
		MenuItem unregisterItem = new MenuItem("Unregister");
		unregisterItem.setOnAction(this::unregister);
		logoutMenu.getItems().add(logoutItem);
		logoutMenu.getItems().add(unregisterItem);

		Menu activitiesMenu = new Menu("Activity log");
		activitiesMenu.setGraphic(new ImageView("file:../Design/activityLog.png"));
		activitiesMenu.setStyle("-fx-accent: #a67a53");
		MenuItem actItem = new MenuItem("Show activity log");
		actItem.setOnAction(this::showActivityLog);
		activitiesMenu.getItems().add(actItem);

		Menu locationMenu = new Menu("Locations");
		locationMenu.setGraphic(new ImageView("file:../Design/location.png"));
		locationMenu.setStyle("-fx-accent: #a67a53");
		MenuItem locationItem = new MenuItem("Show visited locations");
		locationItem.setOnAction(this::showLocations);
		locationMenu.getItems().add(locationItem);

		Menu changePasswordMenu = new Menu("Change password");
		changePasswordMenu.setGraphic(new ImageView("file:../Design/passwordChange.png"));
		changePasswordMenu.setStyle("-fx-accent: #a67a53");
		MenuItem changePasswordItem = new MenuItem("New password");
		changePasswordItem.setOnAction(this::changePassword);
		changePasswordMenu.getItems().add(changePasswordItem);

		mainMenu.getMenus().add(logoutMenu);
		mainMenu.getMenus().add(activitiesMenu);
		mainMenu.getMenus().add(locationMenu);
		mainMenu.getMenus().add(changePasswordMenu);

		sendMsgButton.setOnAction(this::sendMessage);
		uploadDocsButton.setOnAction(this::uploadDocuments);
		viewDocsButton.setOnAction(this::viewDocuments);
		sendLocationButton.setOnAction(this::sendLocation);

		initializeMap();
	}

	/**
	 * Opens window that shows user's activity in application.
	 * 
	 * @param event
	 */
	private void showActivityLog(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/ActivityLogDialog.fxml");
	}

	private void changePassword(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/ChangePasswordDialog.fxml");
	}

	/**
	 * Unregisters user from the application.
	 * 
	 * @param event
	 */
	private void unregister(ActionEvent event) {

	}

	/**
	 * Unregisters user from the application.
	 * 
	 * @param event
	 */
	private void sendMessage(ActionEvent event) {
		String text = ((TextArea) medicalStaffMessagesData.get(0)).getText();
		TextArea sentMessageArea = new TextArea();
		sentMessageArea.setText("You[sent]: " + text);
		medicalStaffMessagesData.add(sentMessageArea);
		chatService.sendMessage(text);
	}

	private void initializeChatSpace() {
		Bindings.bindContentBidirectional(medicalStaffMessagesData, medicalStaffMessages.getChildren());
		TextArea workingSpaceArea = new TextArea();
		workingSpaceArea.setMinHeight(20);
		medicalStaffMessagesData.add(workingSpaceArea);
		Separator workingSpaceSeparator = new Separator(Orientation.HORIZONTAL);
		workingSpaceArea.setMinHeight(3);
		medicalStaffMessagesData.add(workingSpaceSeparator);
	}

	@Override
	public void notify(String message) {
		Platform.runLater(() -> {
			TextArea textArea = new TextArea();
			textArea.setText("Medical staff[response]: " + message);
			medicalStaffMessagesData.add(textArea);
		});
	}

	/**
	 * Action that logs out the user from the application.
	 * 
	 * @param event
	 */
	private void logout(ActionEvent event) {
		LocalDateTime logInAt = User.getInstance().getLoggedInAt();
		LocalDateTime logOutAt = LocalDateTime.now().plusHours(2);
		ActivityLog activityLog = new ActivityLog(logInAt, logOutAt);

		Task<SUResultMessage<Boolean>> task = new Task<SUResultMessage<Boolean>>() {
			@Override
			public SUResultMessage<Boolean> call() throws Exception {

				SUResultMessage<Boolean> isAdded = activityService.add(activityLog, User.getInstance().getToken());
				return isAdded;
			}
		};
		task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM));
		new Thread(task).start();
		StageUtil.switchStage(mainMenu, "/pro/artse/user/fxml/LoginForm.fxml");
	}

	private void sendLocation(ActionEvent event) {
		Location location = buildLocation();
		if (location != null) {
			Task<SUResultMessage<Boolean>> task = new Task<SUResultMessage<Boolean>>() {
				@Override
				public SUResultMessage<Boolean> call() throws Exception {
					SUResultMessage<Boolean> isAdded = locationService.add(location, User.getInstance().getToken());
					return isAdded;
				}
			};
			task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM));
			task.setOnSucceeded(e -> {
				if (task.getValue().isSuccess()) {
					Marker newLocation = Marker.createProvided(Marker.Provided.BLUE)
							.setPosition(new Coordinate(location.getLongitude(), location.getLatitude()));
					mapView.addMarker(newLocation);
				}
			});
			new Thread(task).start();
		}
	}

	/**
	 * Action that shows window with locations user registered at the time.
	 * 
	 * @param event
	 */
	private void showLocations(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/LocationsForm.fxml");
	}

	private void uploadDocuments(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/SendFilesForm.fxml");
	}

	private void viewDocuments(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/DocumentsForm.fxml");
	}

	private void initializeMap() {
		Coordinate banjaLukaCenterCoordinates = new Coordinate(44.77777556721956, 17.191052994189608);
		mapView.setCenter(banjaLukaCenterCoordinates);

		mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
			event.consume();
			latitudeBox.setText(event.getCoordinate().getLatitude().toString());
			longitudeBox.setText(event.getCoordinate().getLongitude().toString());
		});
		mapView.initialize();
	}

	private Location buildLocation() {
		LocalDate since = sinceDatePicker.getValue();
		LocalDate until = untilDatePicker.getValue();
		String sinceTimeString = sinceTimeTextField.getText();
		String untilTimeString = untilTimeTextField.getText();
		String longitudeString = longitudeBox.getText();
		String latitudeString = latitudeBox.getText();

		if (Validator.areNullOrEmpty(sinceTimeString, untilTimeString, longitudeString, latitudeString)) {
			UserAlert.alert(AlertType.ERROR, UserAlert.REQUIRED_FIELDS);
			return null;
		}
		if (!Validator.areValidDates(since, until)) {
			UserAlert.alert(AlertType.ERROR, "Wrong since or until date. Since cannot be greater than until.");
			return null;
		}
		double longitude = Double.parseDouble(longitudeBox.getText());
		double latitude = Double.parseDouble(latitudeBox.getText());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime sinceLocalDateTime = LocalDateTime.of(since, LocalTime.parse(sinceTimeString, formatter));
		LocalDateTime untilLocalDateTime = LocalDateTime.of(until, LocalTime.parse(untilTimeString, formatter));
		return new Location(longitude, latitude, sinceLocalDateTime, untilLocalDateTime);
	}
}