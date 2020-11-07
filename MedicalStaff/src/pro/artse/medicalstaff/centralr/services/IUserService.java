package pro.artse.medicalstaff.centralr.services;

import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.KeyUserInfo;

public interface IUserService {
	MSResultMessage<KeyUserInfo[]> getAll() throws Exception;
}
