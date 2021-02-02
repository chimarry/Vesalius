package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.util.RestApiUtil;

public class UserService implements IUserService {

	@Override
	public SUResultMessage<Boolean> unregister(String token) {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.DELETE_UNREGISTER, "DELETE",
				false);
		try (BufferedReader reader = RestApiUtil.getReader(connection)) {
			String resultString = reader.readLine();
			System.out.println(resultString);
			SUResultMessage<Boolean> deletedResult = pro.artse.user.util.Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return deletedResult;
		} catch (Exception e) {
			// TODO: Add logger
			return ErrorHandler.handle(e, connection);
		}
	}
}
