package pro.artse.centralr.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.ILocationManager;
import pro.artse.dal.models.LocationDTO;

public class LocationManager implements pro.artse.centralr.managers.ILocationManager {

	private ILocationManager locationManager = ManagerFactory.getLocationManager();

	@Override
	public CrResultMessage<Boolean> saveLocation(String token, LocationWrapper location) {
		LocationDTO dto = Mapper.mapToDTO(location);
		DBResultMessage<Boolean> isAdded = locationManager.saveLocation(token, dto);
		return Mapper.mapFrom(isAdded);
	}

	@Override
	public CrResultMessage<List<LocationWrapper>> getAll(String token, int days) {
		DBResultMessage<List<LocationDTO>> locations = locationManager.getLocations(token, days);
		List<LocationWrapper> wrappedLocations = locations.isSuccess() ? locations.getResult().stream()
				.map(x -> Mapper.mapToWrapper(x)).collect(Collectors.toCollection(ArrayList<LocationWrapper>::new))
				: null;
		return new CrResultMessage<List<LocationWrapper>>(wrappedLocations, Mapper.mapStatus(locations.getStatus()),
				locations.getMessage());
	}
}
