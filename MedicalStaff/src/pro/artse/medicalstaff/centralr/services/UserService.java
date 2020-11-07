package pro.artse.medicalstaff.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.KeyUserInfo;
import pro.artse.medicalstaff.util.Mapper;
import pro.artse.medicalstaff.util.RestApiUtil;

public class UserService implements IUserService {

	@Override
	public MSResultMessage<KeyUserInfo[]> getAll() throws Exception {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(ApiPaths.GET_ALL_USERS, "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			MSResultMessage<KeyUserInfo[]> resultActivities = Mapper.mapFromCR(resultString, KeyUserInfo[].class);
			connection.disconnect();
			return resultActivities;
		} catch (IOException e) {
			// TODO Add logger
			connection.disconnect();
			throw e;
		} catch (Exception e) {
			// TODO Add logger
			connection.disconnect();
			throw e;
		}
	}
}
