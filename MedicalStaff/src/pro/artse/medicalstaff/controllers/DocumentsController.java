package pro.artse.medicalstaff.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MedicalStaffAlert;
import pro.artse.medicalstaff.managers.IFileServerManager;
import pro.artse.medicalstaff.models.MedicalDocument;
import pro.artse.medicalstaff.util.ITokenSetup;

public class DocumentsController implements Initializable, ITokenSetup {

	private final IFileServerManager fileServer = ManagersFactory.getFileServerManager();

	private String token;

	@FXML
	private TableView<MedicalDocument> documents;

	@FXML
	private TableColumn<MedicalDocument, String> link;

	@FXML
	private TableColumn<MedicalDocument, String> createdOn;

	@FXML
	private TableColumn<MedicalDocument, String> size;

	@FXML
	private Label downloadProgressLabel;

	@FXML
	private Button downloadButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		downloadButton.setOnAction(this::download);

		link.setCellValueFactory(new PropertyValueFactory<>("FileName"));
		createdOn.setCellValueFactory(document -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			property.setValue(formatter.format(document.getValue().getCreatedOn()));
			return property;
		});

		size.setCellValueFactory(document -> {
			SimpleStringProperty property = new SimpleStringProperty();
			double sizeInKb = document.getValue().getSizeInBytes() / (1024.0);
			property.setValue(Double.toString(Math.round(sizeInKb * 100.0) / 100.0));
			return property;
		});

		Task<MSResultMessage<List<MedicalDocument>>> task = new Task<MSResultMessage<List<MedicalDocument>>>() {
			@Override
			public MSResultMessage<List<MedicalDocument>> call() throws Exception {
				return fileServer.getUserFiles(token);
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<List<MedicalDocument>> resultMessage = task.getValue();
			if (resultMessage.isSuccess())
				documents.getItems().setAll(resultMessage.getResult());
			else
				MedicalStaffAlert.processResult(resultMessage);
		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, MedicalStaffAlert.REMOTE_CONNECTION_PROBLEM);
		});
		new Thread(task).start();
	}

	private void download(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		Stage currentStage = (Stage) downloadButton.getParent().getScene().getWindow();
		File file = chooser.showDialog(currentStage);

		MedicalDocument selectedDocument = documents.getSelectionModel().getSelectedItem();

		updateDownloadStatus("-fx-text-fill: black;", "Downloading...");
		Task<MSResultMessage<byte[]>> task = new Task<MSResultMessage<byte[]>>() {
			@Override
			public MSResultMessage<byte[]> call() throws Exception {
				return fileServer.downloadFile(selectedDocument.getFileName(), token);
			}
		};
		task.setOnSucceeded(e -> {
			MSResultMessage<byte[]> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				try {
					Files.write(Paths.get(file.getAbsolutePath() + File.separator + selectedDocument.getFileName()),
							resultMessage.getResult());
				} catch (IOException e1) {
					MedicalStaffAlert.alert(AlertType.ERROR, "Directory is invalid");
				}
				updateDownloadStatus("-fx-text-fill: green;", "Download completed!");
			} else {
				updateDownloadStatus("-fx-text-fill: red;",
						"Ups! Something went wrong... Status: " + resultMessage.getStatus());
			}
		});
		task.setOnFailed(e -> {
			MedicalStaffAlert.alert(AlertType.ERROR, MedicalStaffAlert.REMOTE_CONNECTION_PROBLEM);
		});
		new Thread(task).start();
	}

	private void updateDownloadStatus(String style, String message) {
		downloadProgressLabel.setStyle(style);
		downloadProgressLabel.setText(message);
	}

	@Override
	public void setToken(String token) {
		this.token = token;
	}
}