package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.core.Response;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;

/**
 * Class that maps application status of operation into HTTP status code, and
 * forms the response.
 * 
 * @author Marija
 *
 */
public final class HttpResultMessage {

	public enum OperationType {
		CREATE, UPDATE, REMOVE, GET, UNSPECIFIED
	}

	public static final String EXISTS_MESSAGE = "Resource already exists.";
	public static final String NOT_FOUND = "Resource does not exists.";
	public static final String ACCEPTED = "Successful.";
	public static final String INTERNAL_SERVER_ERROR = "Error happend on server. Please, try later.";
	public static final String UNKNOWN_ERROR = "Error happend without known cause. Please contact our support.";
	public static final String BAD_REQUEST = "Inputed data is not valid.";

	/**
	 * Builds HTTP response.
	 * 
	 * @param <T>    Type of the entity.
	 * @param result Result of operation.
	 * @param Type   of the operation.
	 * @return Configured HTTP response. @see Response.Status
	 */
	public static <T> Response GetResponse(ResultMessage<T> result, OperationType type) {
		if (result.isSuccess())
			return ok(result, type);
		return error(result);
	}

	/**
	 * Builds HTTP response, where entity is given as list object.
	 * 
	 * @param <T>  Type of elements in list.
	 * @param list List to send over HTTP.
	 * @return Configured HTTP response. @see Response.Status
	 */
	public static <T> Response getResponse(List<T> list) {
		return Response.status(Response.Status.ACCEPTED).entity(list).build();
	}

	/**
	 * Configures HTTP response in case that user is not authorized.
	 * 
	 * @return Configured HTTP response. @see Response.Status
	 */
	public static Response unauthorized() {
		return Response.status(Response.Status.UNAUTHORIZED).entity("You are not authorized to access Central register")
				.build();
	}

	/**
	 * Builds HTTP response.
	 * 
	 * @param <T>    Type of the entity.
	 * @param result Result of operation.
	 * @return Configured HTTP response. @see Response.Status
	 */
	public static <T> Response getResponse(ResultMessage<T> result) {
		if (result.isSuccess())
			return ok(result, OperationType.UNSPECIFIED);
		return error(result);
	}

	public static <T> Response ok(ResultMessage<T> result, OperationType type) {
		return Response.status(type == OperationType.CREATE ? Response.Status.CREATED : Response.Status.ACCEPTED)
				.entity(result).build();
	}

	public static <T> Response error(ResultMessage<T> result) {
		return Response.status(mapToHttp(result.getOperationStatus())).entity(result).build();
	}

	/**
	 * Maps corresponding OperationStatus code into HTTP status code.
	 * 
	 * @param operationStatus Provided operation status.
	 * @return HTTP status code.
	 */
	private static Response.Status mapToHttp(OperationStatus operationStatus) {
		switch (operationStatus) {
		case SUCCESS:
			return Response.Status.ACCEPTED;
		case UNKNOWN_ERROR:
			return Response.Status.INTERNAL_SERVER_ERROR;
		case SERVER_ERROR:
			return Response.Status.INTERNAL_SERVER_ERROR;
		case USER_ERROR:
			return Response.Status.BAD_REQUEST;
		case EXISTS:
			return Response.Status.FOUND;
		case NOT_FOUND:
			return Response.Status.NO_CONTENT;
		default:
			return Response.Status.INTERNAL_SERVER_ERROR;
		}
	}
}