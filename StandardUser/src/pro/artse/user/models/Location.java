package pro.artse.user.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;
	private double longitude;
	private double latitude;
	private String since;
	private String until;

	public Location() {
		super();
	}

	public Location(double longitude, double latitude, String since, String until) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.since = since;
		this.until = until;
	}
	
	public Location(double longitude, double latitude, LocalDateTime since, LocalDateTime until) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.since = since.toString();
		this.until = until.toString();
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
}
