package pro.artse.fileserver.errorhandling;

import java.io.Serializable;

public class FSResultMessage<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private T result;
	private FSStatus status;
	private String message = "";

	public FSResultMessage() {
		super();
	}

	public FSResultMessage(T result, FSStatus status, String message) {
		this(result, status);
		this.message = message;
	}

	public FSResultMessage(T result, FSStatus status) {
		this(status);
		this.result = result;
	}

	public FSResultMessage(FSStatus status) {
		this();
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public FSStatus getStatus() {
		return status;
	}

	public void setStatus(FSStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return status == FSStatus.SUCCESS;
	}
}