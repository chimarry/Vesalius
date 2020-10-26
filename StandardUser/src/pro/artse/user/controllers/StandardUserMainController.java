package pro.artse.user.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import pro.artse.user.centralr.services.IActivityLogService;
import pro.artse.user.centralr.services.ManagersFactory;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.util.StageUtil;
import pro.artse.user.util.UserAlert;

public class StandardUserMainController implements Initializable {
	private static final IActivityLogService activityService = ManagersFactory.getActivityLogService();

	@FXML
	private MenuBar mainMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	 * Action that logs out the user from the application.
	 * 
	 * @param event
	 */
	private void logout(ActionEvent event) {
		LocalDateTime logInAt = LocalDateTime
				.parse(Preferences.userRoot().get("logInAt", LocalDateTime.MIN.toString()));
		LocalDateTime logOutAt = LocalDateTime.now().plusHours(2);
		ActivityLog activityLog = new ActivityLog(logInAt, logOutAt);

		Task<Boolean> task = new Task<Boolean>() {
			@Override
			public Boolean call() throws Exception {

				boolean isAdded = activityService.add(activityLog, Preferences.userRoot().get("token", null));
				return isAdded;
			}
		};
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
