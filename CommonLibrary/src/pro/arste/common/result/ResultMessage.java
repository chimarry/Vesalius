package pro.arste.common.result;

public class ResultMessage<T> {

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
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return operationStatus == OperationStatus.SUCCESS;
	}
}
