package pro.artse.medicalstaff.errorhandling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.rmi.RemoteException;
import java.util.zip.DataFormatException;

import pro.arste.logger.IVesaliusLogger;
import pro.arste.logger.LoggerFactory;

public class ErrorHandler {
	private static final String OUTPUT_LOG_FILE = "medical_staff_logs.log";
	private static final IVesaliusLogger LOGGER = LoggerFactory.getLogger(OUTPUT_LOG_FILE);

	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection, T result) {
		MSResultMessage<T> resultMessage = handle(ex, connection);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> MSResultMessage<T> handle(Exception ex, HttpURLConnection connection) {
		if (connection != null)
			connection.disconnect();
		return handle(ex);
	}

	public static <T> MSResultMessage<T> handle(Exception ex) {
		LOGGER.log(ex);
		if (ex instanceof DataFormatException)
			return new MSResultMessage<T>(null, MSStatus.INVALID_DATA, "File could not be decompressed");
		else if (ex instanceof RemoteException)
			return new MSResultMessage<T>(null, MSStatus.SERVER_ERROR, MedicalStaffAlert.REMOTE_CONNECTION_PROBLEM);
		else if (ex instanceof IOException)
			return new MSResultMessage<T>(null, MSStatus.SERVER_ERROR,
					MedicalStaffAlert.CENTRAL_REGISTER_CONNECTION_PROBLEM);
		return new MSResultMessage<T>(null, MSStatus.UNKNOWN_ERROR, "Exception occured");
	}
}
