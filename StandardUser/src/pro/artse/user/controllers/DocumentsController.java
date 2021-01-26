package pro.artse.user.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.factories.ManagersFactory;
import pro.artse.user.managers.IFileServerManager;
import pro.artse.user.models.MedicalDocument;
import pro.artse.user.models.User;

public class DocumentsController implements Initializable {

	private final IFileServerManager fileServer = ManagersFactory.getFileServerManager();

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

		Task<SUResultMessage<List<MedicalDocument>>> task = new Task<SUResultMessage<List<MedicalDocument>>>() {
			@Override
			public SUResultMessage<List<MedicalDocument>> call() throws Exception {
				return fileServer.getUserFiles(User.getInstance().getToken());
			}
		};
		task.setOnSucceeded(e -> {
			SUResultMessage<List<MedicalDocument>> resultMessage = task.getValue();
			if (resultMessage.isSuccess())
				documents.getItems().setAll(resultMessage.getResult());
			else
				UserAlert.processResult(resultMessage);
		});
		task.setOnFailed(e -> {
			UserAlert.alert(AlertType.ERROR, UserAlert.REMOTE_CONNECTION_PROBLEM);
		});
		new Thread(task).start();
	}

	private void download(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		Stage currentStage = (Stage) downloadButton.getParent().getScene().getWindow();
		File file = chooser.showDialog(currentStage);

		MedicalDocument selectedDocument = documents.getSelectionModel().getSelectedItem();

		updateDownloadStatus("-fx-text-fill: black;", "Downloading...");
		Task<SUResultMessage<byte[]>> task = new Task<SUResultMessage<byte[]>>() {
			@Override
			public SUResultMessage<byte[]> call() throws Exception {
				return fileServer.downloadFile(selectedDocument.getFileName(), User.getInstance().getToken());
			}
		};
		task.setOnSucceeded(e -> {
			SUResultMessage<byte[]> resultMessage = task.getValue();
			if (resultMessage.isSuccess()) {
				try {
					Files.write(Paths.get(file.getAbsolutePath() + File.separator + selectedDocument.getFileName()),
							resultMessage.getResult());
				} catch (IOException e1) {
					UserAlert.alert(AlertType.ERROR, "Directory is invalid");
					e1.printStackTrace();
				}
				updateDownloadStatus("-fx-text-fill: green;", "Download completed!");
			} else {
				updateDownloadStatus("-fx-text-fill: red;",
						"Ups! Something went wrong... Status: " + resultMessage.getStatus());
			}
		});
		task.setOnFailed(e -> {
			UserAlert.alert(AlertType.ERROR, UserAlert.REMOTE_CONNECTION_PROBLEM);
		});
		new Thread(task).start();
	}

	private void updateDownloadStatus(String style, String message) {
		downloadProgressLabel.setStyle(style);
		downloadProgressLabel.setText(message);
	}
}
