package pro.arste.centralr.errorhandling;

import javax.ws.rs.core.Response;

import pro.artse.centralr.util.UnauthorizedException;

public class ErrorHandler {
	public static <T> CrResultMessage<T> handle(Exception ex, T result) {
		CrResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> CrResultMessage<T> handle(Exception ex) {
		ex.printStackTrace();
		if (ex instanceof UnauthorizedException) {
			return new CrResultMessage<T>(Response.Status.UNAUTHORIZED);
		}
		return new CrResultMessage<T>(Response.Status.INTERNAL_SERVER_ERROR);
	}
}
