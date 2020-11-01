package pro.arste.centralr.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pro.artse.centralr.util.Mapper;

public class CrResultMessage<T> {
	public static final String EXISTS_MESSAGE = "Resource already exists.";
	public static final String NOT_FOUND = "Resource does not exists.";
	public static final String ACCEPTED = "Successful.";
	public static final String INTERNAL_SERVER_ERROR = "Error happend on a server. Please, try again later.";
	public static final String UNKNOWN_ERROR = "Error happend without a known cause. Please contact our support.";
	public static final String BAD_REQUEST = "Inputed data is not valid.";
	public static final String UNAUTHORIZED = "You are not authorized to access Central register";
	
	private T result;
	private Status httpStatusCode;
	private String message;

	public CrResultMessage(T result, String status, String message) {
		this(result, Mapper.mapStatus(status), message);
	}

	public CrResultMessage(T result, Status code, String message) {
		this(result, code);
		this.message = message;
	}

	public CrResultMessage(T result, Status code) {
		this(code);
		this.result = result;
	}

	public CrResultMessage(Status status, String message) {
		this(null, status, message);
	}

	public CrResultMessage(Status status) {
		this();
		this.httpStatusCode = status;
	}

	public CrResultMessage() {
	}

	public Response buildResponse() {
		return httpStatusCode != Status.NO_CONTENT ? Response.status(httpStatusCode).entity(this).build()
				: Response.status(httpStatusCode).build();
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public Status getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Status statusCode) {
		this.httpStatusCode = statusCode;
	}

	public String getMessage() {
		if (message == null)
			switch (httpStatusCode) {
			case INTERNAL_SERVER_ERROR:
				return INTERNAL_SERVER_ERROR;
			case ACCEPTED:
				return ACCEPTED;
			case UNAUTHORIZED:
				return UNAUTHORIZED;
			case BAD_REQUEST:
				return BAD_REQUEST;
			case FOUND:
				return EXISTS_MESSAGE;
			case NO_CONTENT:
				return NOT_FOUND;
			default:
				return INTERNAL_SERVER_ERROR;
			}
		else
			return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return httpStatusCode == Status.ACCEPTED;
	}
}
