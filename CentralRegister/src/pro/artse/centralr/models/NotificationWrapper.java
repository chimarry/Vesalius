package pro.artse.centralr.models;

public class NotificationWrapper {
	private String token;
	private String fromWhomToken;
	private String name;
	private LocationWrapper location;

	public NotificationWrapper() {
		super();
	}

	public NotificationWrapper(String token, String fromWhomToken, LocationWrapper location) {
		this(token, fromWhomToken, null, location);
	}

	public NotificationWrapper(String token, String fromWhomToken, String name, LocationWrapper location) {
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

	public LocationWrapper getLocation() {
		return location;
	}

	public void setLocation(LocationWrapper location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
