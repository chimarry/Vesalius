package pro.artse.user.errorhandling;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ErrorHandler {
	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		SUResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		// TODO: Use logger
		ex.printStackTrace();
		connection.disconnect();
		if (ex instanceof IOException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		return new SUResultMessage<T>(null, SUStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
