package pro.artse.dal.errorhandling;

import java.time.format.DateTimeParseException;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class ErrorHandler {
	public static <T> DBResultMessage<T> handle(Exception ex, T result) {
		DBResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> DBResultMessage<T> handle(Exception ex) {
		ex.printStackTrace();
		if (ex instanceof JedisConnectionException) {
			return new DBResultMessage<T>(DbStatus.SERVER_ERROR);
		} else if (ex instanceof DateTimeParseException) {
			return new DBResultMessage<T>(DbStatus.INVALID_DATA);
		}
		return new DBResultMessage<T>(DbStatus.UNKNOWN_ERROR);
	}
}
