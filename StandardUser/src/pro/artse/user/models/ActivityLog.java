package pro.artse.user.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Class that holds data for user's activity in application.
 * 
 * @author Marija
 *
 */
public class ActivityLog implements Serializable {

	/**
	 * When did user log in?
	 */
	private String logInAt;

	/**
	 * When did user log out?
	 */
	private String logOutAt;

	/**
	 * How much time user spent in application?
	 */

	private double totalTime;

	public ActivityLog() {

	}

	/**
	 * Creates activity log object from date-time objects. If strings cannot be
	 * parsed, result is set to Unknown.
	 * 
	 * @param logInAt  When did user log in?
	 * @param logOutAt When did user log out?
	 */
	public ActivityLog(LocalDateTime logInAt, LocalDateTime logOutAt) {
		super();
		this.logInAt = logInAt.toString();
		this.logOutAt = logOutAt.toString();
	}

	public void setLogInAt(String logInAt) {
		this.logInAt = logInAt;
	}

	public void setLogOutAt(String logOutAt) {
		this.logOutAt = logOutAt;
	}

	public String getLogInAt() {
		return logInAt;
	}

	public String getLogOutAt() {
		return logOutAt;
	}

	public double getTotalTime() {
		return calculateTimeSpent();
	}

	/**
	 * Returns time spent in hours.
	 * 
	 * @param logInAt  Begin.
	 * @param logOutAt End.
	 * @return
	 */
	private double calculateTimeSpent() {
		long secs = ChronoUnit.SECONDS.between(LocalDateTime.parse(logInAt), LocalDateTime.parse(logOutAt));
		return Math.round(1000.0 * secs / 3600.0) / 1000.0;
	}

	@Override
	public String toString() {
		return logInAt + "#" + logOutAt + "#" + totalTime;
	}
}
