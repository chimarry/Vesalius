package pro.artse.dal.models;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationDTO {
	private String token;
	private String fromWhomToken;
	private LocationDTO location;
	private String name;

	public NotificationDTO() {
		super();
	}

	public NotificationDTO(String token, String fromWhomToken, LocationDTO location) {
		this(token, fromWhomToken, buildName(), location);
	}

	public NotificationDTO(String token, String fromWhomToken, String name, LocationDTO location) {
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

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> mapAttributes() {
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("token", token);
		attributes.put("fromWhomToken", fromWhomToken);
		attributes.put("longitude", String.valueOf(location.getLongitude()));
		attributes.put("latitude", String.valueOf(location.getLatitude()));
		attributes.put("since", location.getSince().toString());
		attributes.put("until", location.getUntil().toString());
		return attributes;
	}

	public static String buildName() {
		UUID uuid = UUID.randomUUID();
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return Base64.getEncoder().encodeToString(bb.array());
	}

	public static NotificationDTO buildNotification(String name, Map<String, String> attibutes) {
		NotificationDTO notification = new NotificationDTO();
		notification.token = attibutes.get("token");
		notification.fromWhomToken = attibutes.get("fromWhomToken");
		notification.name = name;

		double longitude = Double.parseDouble(attibutes.get("longitude"));
		double latitude = Double.parseDouble(attibutes.get("latitude"));
		LocalDateTime since = LocalDateTime.parse(attibutes.get("since"));
		LocalDateTime until = LocalDateTime.parse(attibutes.get("until"));
		notification.setLocation(new LocationDTO(longitude, latitude, since, until));
		return notification;
	}
}
