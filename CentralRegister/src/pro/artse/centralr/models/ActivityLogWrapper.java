package pro.artse.centralr.models;

import java.time.LocalDateTime;

public class ActivityLogWrapper {
	/**
	 * When did user log in?
	 */
	private String logInAt;

	/**
	 * When did user log out?
	 */
	private String logOutAt;

	public ActivityLogWrapper() {

	}

	public ActivityLogWrapper(LocalDateTime logInAt, LocalDateTime logOutAt) {
		super();
		this.logInAt = logInAt.toString();
		this.logOutAt = logOutAt.toString();
	}

	public ActivityLogWrapper(String logInAt, String logOutAt) {
		super();
		this.logInAt = logInAt;
		this.logOutAt = logOutAt;
	}

	public String getLogInAt() {
		return logInAt;
	}

	public void setLogInAt(String logInAt) {
		this.logInAt = logInAt;
	}

	public String getLogOutAt() {
		return logOutAt;
	}

	public void setLogOutAt(String logOutAt) {
		this.logOutAt = logOutAt;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void setLogInAtAsDateTime(LocalDateTime logInAt) {
		this.logInAt = logInAt.toString();
	}

	public void setLogOutAtAsDateTime(LocalDateTime logOutAt) {
		this.logOutAt = logOutAt.toString();
	}
}
