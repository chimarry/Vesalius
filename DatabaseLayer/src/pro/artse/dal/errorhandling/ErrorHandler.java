package pro.artse.dal.errorhandling;

import java.time.format.DateTimeParseException;

import pro.arste.logger.IVesaliusLogger;
import pro.arste.logger.LoggerFactory;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ErrorHandler {
	private static IVesaliusLogger logger = LoggerFactory.getLogger("database_layer_logs.log");

	public static <T> DBResultMessage<T> handle(Exception ex, T result) {
		DBResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> DBResultMessage<T> handle(Exception ex) {
		logger.log(ex);
		if (ex instanceof JedisConnectionException) {
			return new DBResultMessage<T>(null, DbStatus.SERVER_ERROR, "Jedis connection problem.");
		} else if (ex instanceof DateTimeParseException) {
			return new DBResultMessage<T>(DbStatus.INVALID_DATA);
		} else if (ex instanceof ArrayIndexOutOfBoundsException)
			return new DBResultMessage<T>(DbStatus.SERVER_ERROR);
		return new DBResultMessage<T>(DbStatus.UNKNOWN_ERROR);
	}
}
