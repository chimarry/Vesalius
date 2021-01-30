package pro.artse.centralr.models;

import java.time.LocalDateTime;

public class LocationWrapper {

	private double longitude;
	private double latitude;
	private String since;
	private String until;

	public LocationWrapper(double longitude, double latitude, String since, String until) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.since = since;
		this.until = until;
	}

	public LocationWrapper(double longitude, double latitude, LocalDateTime since, LocalDateTime until) {
		this(longitude, latitude, since.toString(), until.toString());
	}

	public LocationWrapper() {
		super();
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
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

	public void setSinceAsDateTime(LocalDateTime since) {
		this.since = since.toString();
	}

	public void setUntilAsDateTime(LocalDateTime until) {
		this.until = until.toString();
	}
}
