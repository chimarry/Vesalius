package pro.artse.user.models;

public class FlatNotification {
	private String fromWhomToken;
	private String since;
	private String until;
	private String longitude;
	private String latitude;

	public static FlatNotification mapFrom(Notification notification) {
		FlatNotification view = new FlatNotification();
		view.setFromWhomToken(notification.getFromWhomToken());
		view.setSince(notification.getLocation().getSince());
		view.setUntil(notification.getLocation().getUntil());
		view.setLongitude(Double.toString(notification.getLocation().getLongitude()));
		view.setLatitude(Double.toString(notification.getLocation().getLatitude()));
		return view;
	}

	public String getFromWhomToken() {
		return fromWhomToken;
	}

	public void setFromWhomToken(String fromWhomToken) {
		this.fromWhomToken = fromWhomToken;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
