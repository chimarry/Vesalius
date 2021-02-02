package pro.artse.dal.models;

import java.time.LocalDateTime;

/**
 * Represents activity in application that is related to specific user.
 * 
 * @author Marija
 *
 */
public class ActivityLogDTO {

	/**
	 * Unique user's identifier.
	 */
	private String token;

	/**
	 * UserDTO's activity.
	 */
	private ActivityDTO activity;

	public ActivityLogDTO(String token, LocalDateTime logInAt, LocalDateTime logOutAt) {
		this(logInAt, logOutAt);
		this.token = token;
	}

	public ActivityLogDTO(LocalDateTime logInAt, LocalDateTime logOutAt) {
		super();
		this.activity = new ActivityDTO(logInAt, logOutAt);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ActivityDTO getActivity() {
		return activity;
	}

	@Override
	public String toString() {
		return activity.toString();
	}

	public static class ActivityDTO {

		/**
		 * When did user log in?
		 */

		private LocalDateTime logInAt;

		/**
		 * At what time did user log out?
		 */

		private LocalDateTime logOutAt;

		public LocalDateTime getLogInAt() {
			return logInAt;
		}

		public LocalDateTime getLogOutAt() {
			return logOutAt;
		}

		public ActivityDTO(LocalDateTime logInAt, LocalDateTime logOutAt) {
			super();
			this.logInAt = logInAt;
			this.logOutAt = logOutAt;
		}

		public ActivityDTO(String fromLine) {
			String[] dateStrings = fromLine.split("#");
			logInAt = LocalDateTime.parse(dateStrings[0]);
			logOutAt = LocalDateTime.parse(dateStrings[1]);
		}

		@Override
		public String toString() {
			return logInAt.toString() + "#" + logOutAt.toString();
		}
	}
}