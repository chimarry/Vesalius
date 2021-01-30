package pro.artse.dal.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocationDTO {

	private double longitude;
	private double latitude;
	private LocalDateTime since;
	private LocalDateTime until;

	public LocationDTO() {
		super();
	}

	public LocationDTO(double longitude, double latitude, LocalDateTime since, LocalDateTime until) {
		this();
		this.longitude = longitude;
		this.latitude = latitude;
		this.since = since;
		this.until = until;
	}

	public void parseDates(String dates, String separator) throws DateTimeParseException {
		String[] dateTimes = dates.split(separator);
		this.since = LocalDateTime.parse(dateTimes[0]);
		this.until = LocalDateTime.parse(dateTimes[1]);
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

	public LocalDateTime getSince() {
		return since;
	}

	public void setSince(LocalDateTime since) {
		this.since = since;
	}

	public LocalDateTime getUntil() {
		return until;
	}

	public void setUntil(LocalDateTime until) {
		this.until = until;
	}
}
