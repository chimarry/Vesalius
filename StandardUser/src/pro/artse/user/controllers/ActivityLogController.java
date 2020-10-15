package pro.artse.user.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.util.RestApiUtil;
import pro.artse.user.util.UserAlert;
import pro.artse.user.util.json.JsonUtil;

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
		logInTime.setCellValueFactory(new PropertyValueFactory<>("LogInAt"));
		logOutTime.setCellValueFactory(new PropertyValueFactory<>("LogOutAt"));
		totalTimeSpent.setCellValueFactory(new PropertyValueFactory<>("TotalTime"));

		Task<List<ActivityLog>> task = new Task<List<ActivityLog>>() {
			@Override
			public List<ActivityLog> call() throws Exception {

				List<ActivityLog> data = getActivities();
				return data;
			}
		};
		task.setOnSucceeded(e -> activityLog.getItems().setAll(task.getValue()));
		task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM));
		new Thread(task).start();
	}

	/**
	 * Gets list of user activities using HTTP connection (Rest).
	 * 
	 * @return
	 * @throws IOException
	 */
	public ArrayList<ActivityLog> getActivities() throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(Preferences.userRoot().get("token", null),
				ApiPaths.GET_ALL_ACTIVITIES, "GET", false);
		if (connection == null) {
			UserAlert.alert(AlertType.ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		}
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			ArrayList<ActivityLog> activities = JsonUtil.deserialize(JsonUtil.readJsonList(bufferedReader),
					ActivityLog.class);
			connection.disconnect();
			return activities;
		} catch (IOException e) {
			connection.disconnect();
			throw e;
		}
	}
}
