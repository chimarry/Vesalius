package pro.artse.dal.managers;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.LocationData;

public interface ILocationManager {
	DBResultMessage<Boolean> saveLocation(String token, LocationData locationData);
}
