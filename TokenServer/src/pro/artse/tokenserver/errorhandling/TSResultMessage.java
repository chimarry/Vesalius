package pro.artse.tokenserver.errorhandling;

public class TSResultMessage<T> {
	private T result;
	private TSStatus status;
	private String message = "";

	public TSResultMessage(T result, TSStatus status, String message) {
		this(result, status);
		this.message = message;
	}

	public TSResultMessage(T result, TSStatus status) {
		this(status);
		this.result = result;
	}

	public TSResultMessage(TSStatus status) {
		super();
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public TSStatus getStatus() {
		return status;
	}

	public void setStatus(TSStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}