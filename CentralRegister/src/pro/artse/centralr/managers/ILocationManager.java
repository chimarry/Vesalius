package pro.artse.centralr.managers;

import java.util.List;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.LocationWrapper;

public interface ILocationManager {
	CrResultMessage<Boolean> saveLocation(String token, LocationWrapper location);

	CrResultMessage<List<LocationWrapper>> getAll(String token, int days);
}
