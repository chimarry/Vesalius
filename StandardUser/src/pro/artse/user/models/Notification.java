package pro.artse.user.models;

import java.io.Serializable;

public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private String fromWhomToken;
	private String name;
	private Location location;

	public Notification() {
		super();
	}

	public Notification(String token, String fromWhomToken, String name, Location location) {
		this();
		this.token = token;
		this.fromWhomToken = fromWhomToken;
		this.location = location;
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFromWhomToken() {
		return fromWhomToken;
	}

	public void setFromWhomToken(String fromWhomToken) {
		this.fromWhomToken = fromWhomToken;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
