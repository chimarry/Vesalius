package pro.artse.medicalstaff.centralr.services;

import java.io.IOException;

import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.Location;

public interface ILocationService {
	MSResultMessage<Location[]> getAll(String token, int days) throws IOException;
}
