package pro.artse.dal.models;

import java.time.LocalDateTime;

public class LocationData {

	private double longitude;
	private double latitude;
	private LocalDateTime arrivedOn;
	private LocalDateTime leftOn;

	public LocationData() {
		super();
	}

	public LocationData(double longitude, double latitude, LocalDateTime arrivedOn, LocalDateTime leftOn) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.arrivedOn = arrivedOn;
		this.leftOn = leftOn;
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

	public LocalDateTime getArrivedOn() {
		return arrivedOn;
	}

	public void setArrivedOn(LocalDateTime arrivedOn) {
		this.arrivedOn = arrivedOn;
	}

	public LocalDateTime getLeftOn() {
		return leftOn;
	}

	public void setLeftOn(LocalDateTime leftOn) {
		this.leftOn = leftOn;
	}
}
