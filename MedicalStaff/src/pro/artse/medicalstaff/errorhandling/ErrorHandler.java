package pro.artse.medicalstaff.errorhandling;

import java.net.HttpURLConnection;

public class ErrorHandler {
	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		MSResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		// TODO: Use logger
		ex.printStackTrace();
		connection.disconnect();
		return new MSResultMessage<T>(null, MSStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
