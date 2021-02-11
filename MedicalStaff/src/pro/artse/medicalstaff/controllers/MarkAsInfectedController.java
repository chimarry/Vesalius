package pro.artse.medicalstaff.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import pro.artse.medicalstaff.centralr.services.IUserService;
import pro.artse.medicalstaff.centralr.services.WebServiceFactory;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MSStatus;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.models.Location;
import pro.artse.medicalstaff.util.StageUtil;

public class MarkAsInfectedController extends LocationController {
	private final IUserService userService = WebServiceFactory.getUserService();

	@FXML
	private Button markAsInfectedButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		markAsInfectedButton.setOnAction(this::markAsInfected);
	}

	@Override
	public int getDays() {
		return Integer.MAX_VALUE;
	}

	private void markAsInfected(ActionEvent event) {
		Location location = locations.getSelectionModel().getSelectedItem();
		if (location == null) {
			MedicalStaffAlert.alert(AlertType.ERROR, "Choose location!");
			return;
		}
		Task<MSResultMessage<Boolean>> task = new Task<MSResultMessage<Boolean>>() {
			@Override
			public MSResultMessage<Boolean> call() throws Exception {
				MSResultMessage<Boolean> data = userService.markUserAsInfected(token, location);
				return data;
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<Boolean> resultMessage = task.getValue();
			if (resultMessage.getStatus() == MSStatus.NOT_FOUND) {
				MedicalStaffAlert.processResult(resultMessage, AlertType.INFORMATION);
			} else
				MedicalStaffAlert.processResult(resultMessage, AlertType.ERROR);
			StageUtil.closeDialog(markAsInfectedButton);
		});

		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, "Connection with Central register failed.");
		});
		new Thread(task).start();
	}
}
