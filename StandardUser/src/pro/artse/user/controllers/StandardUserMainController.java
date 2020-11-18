package pro.artse.user.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pro.artse.user.centralr.services.IActivityLogService;
import pro.artse.user.centralr.services.ManagersFactory;
import pro.artse.user.chat.IChatService;
import pro.artse.user.chat.ISubscriber;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.util.StageUtil;

public class StandardUserMainController implements Initializable, ISubscriber {
	private final IActivityLogService activityService = ManagersFactory.getActivityLogService();
	private final IChatService chatService = ManagersFactory.getChatService();

	private ObservableList<Node> medicalStaffMessagesData = FXCollections.<Node>observableArrayList();

	@FXML
	private MenuBar mainMenu;

	@FXML
	private Button sendMsgButton;

	@FXML
	private VBox medicalStaffMessages;

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

		mainMenu.getMenus().add(logoutMenu);
		mainMenu.getMenus().add(activitiesMenu);
		mainMenu.getMenus().add(locationMenu);

		sendMsgButton.setOnAction(this::sendMessage);
	}

	/**
	 * Opens window that shows user's activity in application.
	 * 
	 * @param event
	 */
	private void showActivityLog(ActionEvent event) {
		StageUtil.showDialog("/pro/artse/user/fxml/ActivityLogDialog.fxml");
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
		LocalDateTime logInAt = LocalDateTime
				.parse(Preferences.userRoot().get("logInAt", LocalDateTime.MIN.toString()));
		LocalDateTime logOutAt = LocalDateTime.now().plusHours(2);
		ActivityLog activityLog = new ActivityLog(logInAt, logOutAt);

		Task<SUResultMessage<Boolean>> task = new Task<SUResultMessage<Boolean>>() {
			@Override
			public SUResultMessage<Boolean> call() throws Exception {

				SUResultMessage<Boolean> isAdded = activityService.add(activityLog,
						Preferences.userRoot().get("token", null));
				return isAdded;
			}
		};
		task.setOnSucceeded(e -> {
			UserAlert.processResult(task.getValue());
		});
		task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM));
		new Thread(task).start();
		StageUtil.switchStage(mainMenu, "/pro/artse/user/fxml/LoginForm.fxml");
	}

	/**
	 * Action that shows window with locations user registered at the time.
	 * 
	 * @param event
	 */
	private void showLocations(ActionEvent event) {

	}
}
