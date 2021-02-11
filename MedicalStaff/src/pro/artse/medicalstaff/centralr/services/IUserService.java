package pro.artse.medicalstaff.centralr.services;

import java.io.IOException;

import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.KeyUserInfo;
import pro.artse.medicalstaff.models.Location;

public interface IUserService {
	MSResultMessage<KeyUserInfo[]> getAll() throws Exception;

	MSResultMessage<KeyUserInfo> search(String token) throws Exception;

	MSResultMessage<Boolean> blockUser(String token) throws Exception;

	MSResultMessage<Boolean> markUserAsInfected(String token, Location location) throws IOException;
	
	MSResultMessage<Boolean> changeCovidStatus(KeyUserInfo userInfo) throws IOException;
}
