package pro.artse.user.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * Class that holds data for user's activity in application.
 * 
 * @author Marija
 *
 */
public class ActivityLog {

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

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

	/**
	 * Creates activity log object from strings. If strings cannot be parsed, result
	 * is set to Unknown.
	 * 
	 * @param logInAt  When did user log in? (Expects date-time format)
	 * @param logOutAt When did user log out? (Expects date-time format)
	 */
	public ActivityLog(String logInAt, String logOutAt) {
		super();
		try {
			LocalDateTime time1 = LocalDateTime.parse(logInAt);
			LocalDateTime time2 = LocalDateTime.parse(logOutAt);
			initialize(time1, time2);
		} catch (Exception e) {
			this.logOutAt = "Unknown";
			this.logInAt = "Unknown";
		}
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
		initialize(logInAt, logOutAt);
	}

	public static DateTimeFormatter getFormatter() {
		return formatter;
	}

	public String getLogInAt() {
		return logInAt;
	}

	public String getLogOutAt() {
		return logOutAt;
	}

	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * Returns time spent in hours.
	 * 
	 * @param logInAt  Begin.
	 * @param logOutAt End.
	 * @return
	 */
	private double calculateTimeSpent(LocalDateTime logInAt, LocalDateTime logOutAt) {
		long secs = ChronoUnit.SECONDS.between(logInAt, logOutAt);
		return secs / 3600.0;
	}

	/**
	 * Initializes log in and log out time with proper formatter, and sets total
	 * time spent in application.
	 * 
	 * @param logInAt  When did user log in?
	 * @param logOutAt When did user log out?
	 */
	private void initialize(LocalDateTime logInAt, LocalDateTime logOutAt) {
		this.totalTime = calculateTimeSpent(logInAt, logOutAt);
		this.logInAt = formatter.format(logInAt);
		this.logOutAt = formatter.format(logOutAt);
	}

	@Override
	public String toString() {
		return logInAt + "#" + logOutAt + "#" + totalTime;
	}
}
