package pro.artse.user.errorhandling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.RemoteException;
import java.util.zip.DataFormatException;

public class ErrorHandler {
	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		SUResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		ex.printStackTrace();
		connection.disconnect();
		return handle(ex);
	}

	public static <T> SUResultMessage<T> handle(Exception ex) {
		// TODO: Use logger
		if (ex instanceof DataFormatException)
			return new SUResultMessage<T>(null, SUStatus.INVALID_DATA, "File could not be decompressed");
		else if (ex instanceof RemoteException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, UserAlert.REMOTE_CONNECTION_PROBLEM);
		else if (ex instanceof IOException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		return new SUResultMessage<T>(null, SUStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
