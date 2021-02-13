package pro.artse.user.errorhandling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.RemoteException;
import java.util.zip.DataFormatException;

import com.esotericsoftware.kryo.KryoException;
import com.google.gson.JsonSyntaxException;

import pro.arste.logger.IVesaliusLogger;
import pro.arste.logger.LoggerFactory;

public class ErrorHandler {
	private static final String OUTPUT_LOG_FILE ="standard_user_logs.log";
	private static final IVesaliusLogger LOGGER = LoggerFactory.getLogger(OUTPUT_LOG_FILE);

	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		SUResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> SUResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		if (connection != null)
			connection.disconnect();
		return handle(ex);
	}

	public static <T> SUResultMessage<T> handle(Exception ex) {
		LOGGER.log(ex);
		if (ex instanceof JsonSyntaxException)
			return new SUResultMessage<T>(null, SUStatus.INVALID_DATA, "Invalid json.");
		else if (ex instanceof KryoException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, "Kryo error.");
		else if (ex instanceof DataFormatException)
			return new SUResultMessage<T>(null, SUStatus.INVALID_DATA, "File could not be decompressed");
		else if (ex instanceof RemoteException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, UserAlert.REMOTE_CONNECTION_PROBLEM);
		else if (ex instanceof IOException)
			return new SUResultMessage<T>(null, SUStatus.SERVER_ERROR, UserAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		return new SUResultMessage<T>(null, SUStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
