package pro.artse.user.centralr.services;

import java.io.IOException;
import java.util.List;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Location;

public interface ILocationService {
	SUResultMessage<Boolean> add(Location location, String token) throws IOException;

	SUResultMessage<Location[]> getAll(String token, int days) throws IOException;
}
