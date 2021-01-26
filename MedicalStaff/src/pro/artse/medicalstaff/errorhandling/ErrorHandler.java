package pro.artse.medicalstaff.errorhandling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.RemoteException;

public class ErrorHandler {
	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		MSResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		connection.disconnect();
		return handle(ex);
	}

	public static <T> MSResultMessage<T> handle(Exception ex) {
		// TODO: Use logger
		ex.printStackTrace();
		if (ex instanceof RemoteException)
			return new MSResultMessage<T>(null, MSStatus.SERVER_ERROR, MedicalStaffAlert.REMOTE_CONNECTION_PROBLEM);
		else if (ex instanceof IOException)
			return new MSResultMessage<T>(null, MSStatus.SERVER_ERROR,
					MedicalStaffAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		return new MSResultMessage<T>(null, MSStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
