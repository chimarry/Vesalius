package pro.artse.user.controllers;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Marker.Provided;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.models.FlatNotification;
import pro.artse.user.models.Notification;
import pro.artse.user.models.User;
import pro.artse.user.notifications.NotificationStorage;
import pro.artse.user.notifications.Serializer;
import pro.artse.user.notifications.SerializerFactory;

public class NotificationHistoryController implements Initializable {

	@FXML
	private TableView<FlatNotification> notificationsTable;

	@FXML
	private TableColumn<FlatNotification, String> since;

	@FXML
	private TableColumn<FlatNotification, String> until;

	@FXML
	private TableColumn<FlatNotification, String> fromWhomToken;

	@FXML
	private TableColumn<FlatNotification, String> longitude;

	@FXML
	private TableColumn<FlatNotification, String> latitude;

	@FXML
	private MapView mapView;

	private Marker marker = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fromWhomToken.setCellValueFactory(new PropertyValueFactory<>("fromWhomToken"));
		longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
		latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));

		since.setCellValueFactory(notification -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			property.setValue(formatter.format(LocalDateTime.parse(notification.getValue().getSince())));
			return property;
		});

		until.setCellValueFactory(notification -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			property.setValue(formatter.format(LocalDateTime.parse(notification.getValue().getUntil())));
			return property;
		});

		notificationsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Coordinate coordinate = new Coordinate(Double.valueOf(newSelection.getLatitude()),
						Double.valueOf(newSelection.getLongitude()));
				mapView.removeMarker(marker);
				marker = Marker.createProvided(Provided.BLUE).setPosition(coordinate).setVisible(true);
				mapView.addMarker(marker);
			}
		});

		Task<SUResultMessage<List<Notification>>> task = new Task<SUResultMessage<List<Notification>>>() {
			@Override
			public SUResultMessage<List<Notification>> call() throws Exception {
				ArrayList<Notification> notifications = new ArrayList<>();
				SUResultMessage<List<File>> notificationFiles = NotificationStorage
						.getNotifications(User.getInstance().getToken());
				if (!notificationFiles.isSuccess())
					return new SUResultMessage<List<Notification>>(notifications, notificationFiles.getStatus(),
							notificationFiles.getMessage());
				for (File file : notificationFiles.getResult()) {
					Serializer serializer = SerializerFactory.getBasedOnExtension(file.getName());
					SUResultMessage<Notification> notification = serializer.deserialize(file);
					if (notification.isSuccess()) {
						// TODO: log error
						notifications.add(notification.getResult());
					}
				}
				return new SUResultMessage<List<Notification>>(notifications, SUStatus.SUCCESS);
			}
		};

		task.setOnSucceeded(e -> {
			SUResultMessage<List<Notification>> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				List<Notification> notifications = resultMessage.getResult();
				notificationsTable.getItems().setAll(notifications.stream().map(x -> FlatNotification.mapFrom(x))
						.toArray(x -> new FlatNotification[notifications.size()]));
				initializeMap();
			} else
				UserAlert.processResult(resultMessage);
		});
		task.setOnFailed(e -> {
			UserAlert.alert(AlertType.ERROR, "Notification history could not been shown.");
		});
		new Thread(task).start();
	}

	private void initializeMap() {
		Coordinate banjaLukaCenterCoordinates = new Coordinate(44.77777556721956, 17.191052994189608);
		mapView.setCenter(banjaLukaCenterCoordinates);
		if (marker == null)
			marker = Marker.createProvided(Provided.BLUE).setPosition(banjaLukaCenterCoordinates).setVisible(true);
		mapView.addMarker(marker);
		mapView.initialize();
	}
}
