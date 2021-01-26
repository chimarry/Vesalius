package pro.artse.medicalstaff.errorhandling;

import java.io.Serializable;

public class MSResultMessage<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private T result;
	private MSStatus status;
	private String message = "";

	public MSResultMessage() {
		super();
	}

	public MSResultMessage(T result, MSStatus status, String message) {
		this(result, status);
		this.message = message;
	}

	public MSResultMessage(T result, MSStatus status) {
		this(status);
		this.result = result;
	}

	public MSResultMessage(MSStatus status) {
		this();
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public MSStatus getStatus() {
		return status;
	}

	public void setStatus(MSStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return status == MSStatus.SUCCESS;
	}
}