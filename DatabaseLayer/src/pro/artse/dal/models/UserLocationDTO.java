package pro.artse.dal.models;

public class UserLocationDTO {

	private LocationDTO location;
	private String token;

	public UserLocationDTO(LocationDTO location, String token) {
		super();
		setLocation(location);
		this.token = token;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = new LocationDTO(location.getLongitude(), location.getLongitude(), location.getSince(),
				location.getUntil());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
