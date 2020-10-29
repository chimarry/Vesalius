package pro.artse.user.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pro.artse.user.centralr.services.IActivityLogService;
import pro.artse.user.centralr.services.ManagersFactory;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.models.ActivityLog;

/**
 * Interacts with activity log view.
 * 
 * @author Marija
 *
 */
public class ActivityLogController implements Initializable {

	private static final IActivityLogService activityService = ManagersFactory.getActivityLogService();

	@FXML
	private TableView<ActivityLog> activityLog;

	@FXML
	private TableColumn<ActivityLog, String> logInTime;

	@FXML
	private TableColumn<ActivityLog, String> logOutTime;

	@FXML
	private TableColumn<ActivityLog, Double> totalTimeSpent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInTime.setCellValueFactory(new PropertyValueFactory<>("LogInAt"));
		logOutTime.setCellValueFactory(new PropertyValueFactory<>("LogOutAt"));
		totalTimeSpent.setCellValueFactory(new PropertyValueFactory<>("TotalTime"));

		Task<List<ActivityLog>> task = new Task<List<ActivityLog>>() {
			@Override
			public List<ActivityLog> call() throws Exception {

				List<ActivityLog> data = activityService.getAll(Preferences.userRoot().get("token", null));
				return data;
			}
		};
		task.setOnSucceeded(e -> activityLog.getItems().setAll(task.getValue()));
		task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM));
		new Thread(task).start();

	}
}
