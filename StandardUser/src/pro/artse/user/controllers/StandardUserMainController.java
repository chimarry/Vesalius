package pro.artse.user.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import pro.artse.user.util.StageUtil;

public class StandardUserMainController implements Initializable {

	@FXML
	private MenuBar mainMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Menu logoutMenu = new Menu("Log out");
		logoutMenu.setGraphic(new ImageView("file:../Design/logout.png"));
		logoutMenu.setStyle("-fx-accent: #a67a53;");
		MenuItem logoutItem = new MenuItem("Log out");
		logoutItem.setOnAction(this::logout);
		MenuItem unregisterItem = new MenuItem("Unregister");
		unregisterItem.setOnAction(this::unregister);
		logoutMenu.getItems().add(logoutItem);
		logoutMenu.getItems().add(unregisterItem);

		Menu activitiesMenu = new Menu("Activity log");
		activitiesMenu.setGraphic(new ImageView("file:../Design/activityLog.png"));
		logoutMenu.setStyle("-fx-accent: #a67a53;");
		MenuItem actItem = new MenuItem("Show activity log");
		actItem.setOnAction(this::showActivityLog);
		activitiesMenu.getItems().add(actItem);

		Menu locationMenu = new Menu("Locations");
		locationMenu.setGraphic(new ImageView("file:../Design/location.png"));
		logoutMenu.setStyle("-fx-accent: #a67a53;");
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
