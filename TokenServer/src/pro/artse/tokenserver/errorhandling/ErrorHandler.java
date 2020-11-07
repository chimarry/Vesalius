package pro.artse.tokenserver.errorhandling;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import pro.arste.logger.IVesaliusLogger;
import pro.arste.logger.LoggerFactory;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.errorhandling.DbStatus;

public class ErrorHandler {
	private static IVesaliusLogger logger = LoggerFactory
			.getLogger("C:\\Users\\Marija\\Desktop\\log" + LocalDate.now() + ".log");

	public static <T> DBResultMessage<T> handle(Exception ex, T result) {
		DBResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> DBResultMessage<T> handle(Exception ex) {
		ex.printStackTrace();
		logger.log(ex);
		if (ex instanceof DateTimeParseException) {
			return new DBResultMessage<T>(DbStatus.INVALID_DATA);
		}
		return new DBResultMessage<T>(DbStatus.UNKNOWN_ERROR);
	}
}
