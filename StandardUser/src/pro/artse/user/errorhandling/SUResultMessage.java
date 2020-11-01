package pro.artse.user.errorhandling;

public class SUResultMessage<T> {
	private T result;
	private SUStatus status;
	private String message = "";

	public SUResultMessage() {
		super();
	}

	public SUResultMessage(T result, SUStatus status, String message) {
		this(result, status);
		this.message = message;
	}

	public SUResultMessage(T result, SUStatus status) {
		this(status);
		this.result = result;
	}

	public SUResultMessage(SUStatus status) {
		this();
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public SUStatus getStatus() {
		return status;
	}

	public void setStatus(SUStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return status == SUStatus.SUCCESS;
	}
}