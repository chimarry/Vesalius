package pro.arste.common.result;

public class ResultMessage<T> {

	public static final String EXISTS_MESSAGE = "Resource already exists.";
	public static final String NOT_FOUND = "Resource does not exists.";
	public static final String ACCEPTED = "Successful.";
	public static final String INTERNAL_SERVER_ERROR = "Error happend on server. Please, try later.";
	public static final String UNKNOWN_ERROR = "Error happend without known cause. Please contact our support.";
	public static final String BAD_REQUEST = "Inputed data is not valid.";

	private T result;

	private OperationStatus operationStatus;

	private String message;

	public ResultMessage(T result, OperationStatus operationStatus, String message) {
		this(result, operationStatus);
		this.message = message;
	}

	public ResultMessage(T result, OperationStatus operationStatus) {
		this(operationStatus);
		this.result = result;
	}

	public ResultMessage(OperationStatus operationStatus, String message) {
		this(operationStatus);
		this.message = message;
	}

	public ResultMessage(OperationStatus operationStatus) {
		super();
		this.operationStatus = operationStatus;
		this.message = getMessage();
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public OperationStatus getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(OperationStatus operationStatus) {
		this.operationStatus = operationStatus;
	}

	public String getMessage() {
		if (message == null) {
			switch (operationStatus) {
			case SUCCESS:
				return ACCEPTED;
			case UNKNOWN_ERROR:
				return INTERNAL_SERVER_ERROR;
			case SERVER_ERROR:
				return INTERNAL_SERVER_ERROR;
			case USER_ERROR:
				return BAD_REQUEST;
			case EXISTS:
				return EXISTS_MESSAGE;
			case NOT_FOUND:
				return NOT_FOUND;
			default:
				return INTERNAL_SERVER_ERROR;
			}
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return operationStatus == OperationStatus.SUCCESS;
	}
}
