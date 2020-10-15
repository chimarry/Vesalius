package pro.artse.centralr.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogWrapper {
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	/**
	 * When did user log in?
	 */
	private LocalDateTime logInAt;

	/**
	 * When did user log out?
	 */
	private LocalDateTime logOutAt;

	/**
	 * How much time user spent in application?
	 */

	public ActivityLogWrapper(LocalDateTime logInAt, LocalDateTime logOutAt) {
		super();
		this.logInAt = logInAt;
		this.logOutAt = logOutAt;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public void setLogInAt(LocalDateTime logInAt) {
		this.logInAt = logInAt;
	}

	public void setLogOutAt(LocalDateTime logOutAt) {
		this.logOutAt = logOutAt;
	}

	public LocalDateTime getLogInAt() {
		return logInAt;
	}

	public LocalDateTime getLogOutAt() {
		return logOutAt;
	}
}
