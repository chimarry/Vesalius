package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Marker.Provided;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pro.artse.medicalstaff.centralr.services.ILocationService;
import pro.artse.medicalstaff.centralr.services.WebServiceFactory;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.models.Location;
import pro.artse.medicalstaff.util.ITokenSetup;

public class LocationController implements Initializable, ITokenSetup {

	protected final ILocationService locationService = WebServiceFactory.getLocationService();
	protected String token;

	@FXML
	protected TableView<Location> locations;

	@FXML
	protected TableColumn<Location, String> since;

	@FXML
	protected TableColumn<Location, String> until;

	@FXML
	protected TableColumn<Location, Double> longitude;

	@FXML
	protected TableColumn<Location, Double> latitude;

	@FXML
	protected MapView mapView;

	protected Marker marker = null;

	public int getDays() {
		TextInputDialog td = new TextInputDialog("12");
		td.setHeaderText("Enter number of days");
		Optional<String> daysAsString = td.showAndWait();
		return Integer.parseInt(daysAsString.get());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int days = getDays();
		longitude.setCellValueFactory(new PropertyValueFactory<>("Longitude"));
		latitude.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
		since.setCellValueFactory(location -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			property.setValue(formatter.format(location.getValue().getSinceAsDateTime()));
			return property;
		});

		until.setCellValueFactory(location -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			property.setValue(formatter.format(location.getValue().getUntilAsDateTime()));
			return property;
		});

		locations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Coordinate coordinate = new Coordinate(newSelection.getLatitude(), newSelection.getLongitude());
				mapView.removeMarker(marker);
				marker = Marker.createProvided(Provided.BLUE).setPosition(coordinate).setVisible(true);
				mapView.addMarker(marker);
			}
		});

		Task<MSResultMessage<Location[]>> task = new Task<MSResultMessage<Location[]>>() {
			@Override
			public MSResultMessage<Location[]> call() throws Exception {
				return locationService.getAll(token, days);
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<Location[]> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				locations.getItems().setAll(resultMessage.getResult());
				initializeMap();
			} else
				MedicalStaffAlert.processResult(resultMessage);
		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, MedicalStaffAlert.REMOTE_CONNECTION_PROBLEM);
		});
		new Thread(task).start();

	}

	protected void initializeMap() {
		Coordinate banjaLukaCenterCoordinates = new Coordinate(44.77777556721956, 17.191052994189608);
		mapView.setCenter(banjaLukaCenterCoordinates);
		if (marker == null)
			marker = Marker.createProvided(Provided.BLUE).setPosition(banjaLukaCenterCoordinates).setVisible(true);
		mapView.addMarker(marker);
		mapView.initialize();
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}
}
