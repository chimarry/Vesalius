package pro.artse.user.models;

import java.time.LocalDateTime;

public class User {

	private static final User userInstance = new User();

	public static User getInstance() {
		return userInstance;
	}

	private String token;
	private String passwordHash;
	private boolean isLoggedIn;
	private LocalDateTime loggedInAt;

	private User() {

	}

	public void clear() {
		setToken(null);
		setLoggedIn(false);
		setLoggedInAt(null);
		setPasswordHash(null);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public LocalDateTime getLoggedInAt() {
		return loggedInAt;
	}

	public void setLoggedInAt(LocalDateTime loggedInAt) {
		this.loggedInAt = loggedInAt;
	}
}
