package pro.arste.centralr.errorhandling;

import java.rmi.RemoteException;

import javax.ws.rs.core.Response;
import javax.xml.rpc.ServiceException;

public class ErrorHandler {
	public static final String TOKEN_SERVER_CONNECTION_ERROR = "Could not connect on token server.";
	public static final String SERVICE_EXCEPTION_ERROR = "Service exception";

	public static <T> CrResultMessage<T> handle(Exception ex, T result) {
		CrResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> CrResultMessage<T> handle(Exception ex) {
		if (ex instanceof UnauthorizedException) {
			return new CrResultMessage<T>(((UnauthorizedException) ex).getStatus(), ex.getMessage());
		} else if (ex instanceof RemoteException)
			return new CrResultMessage<T>(Response.Status.INTERNAL_SERVER_ERROR, TOKEN_SERVER_CONNECTION_ERROR);
		else if (ex instanceof ServiceException) {
			return new CrResultMessage<T>(Response.Status.INTERNAL_SERVER_ERROR, SERVICE_EXCEPTION_ERROR);
		}
		return new CrResultMessage<T>(Response.Status.INTERNAL_SERVER_ERROR);
	}
}
