package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.LocationDTO;

public interface ILocationManager {
	DBResultMessage<Boolean> saveLocation(String token, LocationDTO locationData);

	DBResultMessage<List<LocationDTO>> getLocations(String token, int timeIntervalInDays);

	LocationDTO isInRange(String locationToken, LocationDTO location, int distanceInMeters, int timeInterval);

	DBResultMessage<Boolean> deleteAll(String token);
}
