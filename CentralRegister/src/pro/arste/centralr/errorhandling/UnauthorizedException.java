package pro.arste.centralr.errorhandling;

import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends Exception {
	private static final long serialVersionUID = 3935263889605071435L;
	public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to access Central register.";
	public static final String COULD_NOT_VERIFY_MESSAGE = "Due to a connection problems, we could not verify your identity.";

	private Status httpStatusCode;

	public UnauthorizedException(Status status) {
		super(buildMessage(status));
		this.httpStatusCode = status == Status.UNAUTHORIZED ? Status.UNAUTHORIZED : Status.INTERNAL_SERVER_ERROR;
	}

	public static String buildMessage(Status status) {
		switch (status) {
		case NO_CONTENT:
		case UNAUTHORIZED:
			return UNAUTHORIZED_MESSAGE;
		default:
			return COULD_NOT_VERIFY_MESSAGE;
		}
	}

	public Status getStatus() {
		return httpStatusCode;
	}
}
