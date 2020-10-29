package pro.artse.tokenserver.errorhandling;

import java.time.format.DateTimeParseException;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;

public class ErrorHandler {
	public static <T> DBResultMessage<T> handle(Exception ex, T result) {
		DBResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> DBResultMessage<T> handle(Exception ex) {
		ex.printStackTrace();
		if (ex instanceof DateTimeParseException) {
			return new DBResultMessage<T>(DbStatus.INVALID_DATA);
		}
		return new DBResultMessage<T>(DbStatus.UNKNOWN_ERROR);
	}
}
