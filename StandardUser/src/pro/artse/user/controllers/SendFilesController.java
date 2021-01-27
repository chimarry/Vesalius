package pro.artse.user.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.UserAlert;
import pro.artse.user.errorhandling.Validator;
import pro.artse.user.factories.ManagersFactory;
import pro.artse.user.managers.IFileServerManager;
import pro.artse.user.models.User;
import pro.artse.user.util.StageUtil;

public class SendFilesController implements Initializable {

	private final List<File> filesToSend = new ArrayList<File>();
	private final IFileServerManager fileServer = ManagersFactory.getFileServerManager();

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private Label label3;

	@FXML
	private Label label4;

	@FXML
	private Label label5;

	@FXML
	private Button sendFilesButton;

	@FXML
	private Button addFile1Button;

	@FXML
	private Button addFile2Button;

	@FXML
	private Button addFile3Button;

	@FXML
	private Button addFile4Button;

	@FXML
	private Button addFile5Button;

	@FXML
	private Button cancelButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addFile1Button.setOnAction(this::addFile);
		addFile2Button.setOnAction(this::addFile);
		addFile3Button.setOnAction(this::addFile);
		addFile4Button.setOnAction(this::addFile);
		addFile5Button.setOnAction(this::addFile);

		sendFilesButton.setOnAction(this::sendFiles);
	}

	private void sendFiles(ActionEvent event) {
		Task<SUResultMessage<Integer>> task = new Task<SUResultMessage<Integer>>() {
			@Override
			public SUResultMessage<Integer> call() throws Exception {
				SUResultMessage<Integer> filesAdded = fileServer.sendFiles(filesToSend, User.getInstance().getToken());
				return filesAdded;
			}
		};
		task.setOnFailed(e -> UserAlert.alert(AlertType.ERROR, "Uploading failed."));
		task.setOnSucceeded(e -> {
			SUResultMessage<Integer> resultMessage = task.getValue();
			StageUtil.closeDialog(sendFilesButton);
			int filesCount = resultMessage.getResult() == null ? 0 : resultMessage.getResult();
			String message = "Number of files uploaded: " + (filesCount == 0 ? "none" : filesCount);
			UserAlert.alert(AlertType.INFORMATION, message);
		});
		new Thread(task).start();
	}

	private void addFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
		fileChooser.getExtensionFilters().add(extFilter);
		Stage currentStage = (Stage) sendFilesButton.getParent().getScene().getWindow();

		File file = fileChooser.showOpenDialog(currentStage);
		filesToSend.add(file);

		showFilePath(file.getAbsolutePath());
	}

	private void showFilePath(String filePath) {
		if (Validator.IsNullOrEmpty(label1.getText())) {
			label1.setText(filePath);
			addFile1Button.setDisable(true);
		} else if (Validator.IsNullOrEmpty(label2.getText())) {
			label2.setText(filePath);
			addFile2Button.setDisable(true);
		} else if (Validator.IsNullOrEmpty(label3.getText())) {
			label3.setText(filePath);
			addFile3Button.setDisable(true);
		} else if (Validator.IsNullOrEmpty(label4.getText())) {
			label4.setText(filePath);
			addFile4Button.setDisable(true);
		} else {
			label5.setText(filePath);
			addFile5Button.setDisable(true);
		}
	}
}
