package pro.artse.user.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pro.artse.user.models.ActivityLog;

/**
 * Interacts with activity log view.
 * 
 * @author Marija
 *
 */
public class ActivityLogController implements Initializable {

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

		LocalDateTime logInAt = LocalDateTime.now();
		ArrayList<ActivityLog> activityLogs = new ArrayList<>();
		
		activityLogs.add(new ActivityLog(logInAt, logInAt.plusMinutes(30)));
		activityLogs.add(new ActivityLog(logInAt.plusMinutes(40), logInAt.plusMinutes(50)));
		activityLogs.add(new ActivityLog(logInAt.plusMinutes(300), logInAt.plusMinutes(500)));
		ObservableList<ActivityLog> activityLogs2 = FXCollections.observableArrayList(activityLogs);

		logInTime.setCellValueFactory(new PropertyValueFactory<>("LogInAt"));
		logOutTime.setCellValueFactory(new PropertyValueFactory<>("LogOutAt"));
		totalTimeSpent.setCellValueFactory(new PropertyValueFactory<>("TotalTime"));
		activityLog.setItems(activityLogs2);
	}
}
