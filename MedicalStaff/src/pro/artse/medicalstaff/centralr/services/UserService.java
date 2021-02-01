package pro.artse.medicalstaff.centralr.services;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import javax.ws.rs.core.Response;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.medicalstaff.errorhandling.ErrorHandler;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.errorhandling.MSStatus;
import pro.artse.medicalstaff.models.KeyUserInfo;
import pro.artse.medicalstaff.models.Location;
import pro.artse.medicalstaff.util.JsonUtil;
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

	@Override
	public MSResultMessage<KeyUserInfo> search(String token) throws Exception {
		String urlPath = RestApiUtil.buildPath(ApiPaths.GET_ONE_USER, token);
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(urlPath, "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			if (resultString == null && connection.getResponseCode() == Response.Status.NO_CONTENT.getStatusCode())
				return new MSResultMessage<KeyUserInfo>(null, MSStatus.NOT_FOUND, "User not found.");
			MSResultMessage<KeyUserInfo> resultOfSearch = Mapper.mapFromCR(resultString, KeyUserInfo.class);
			connection.disconnect();
			return resultOfSearch;
		} catch (IOException e) {
			// TODO Add logger
			connection.disconnect();
			throw e;
		} catch (Exception e) { // TODO Add logger
			connection.disconnect();
			throw e;
		}

	}

	@Override
	public MSResultMessage<Boolean> blockUser(String token) throws Exception {
		String urlPath = RestApiUtil.buildPath(ApiPaths.DELETE_BLOCK_USER, token);
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(urlPath, "DELETE", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			MSResultMessage<Boolean> isBlocked = Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return isBlocked;
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

	@Override
	public MSResultMessage<Boolean> markUserAsInfected(String token, Location location) throws IOException {
		String urlPath = RestApiUtil.buildPath(ApiPaths.POST_USER_IS_INFECTED, token);
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(urlPath, "POST", true);
		connection.setRequestProperty("Content-Type", "application/json");
		LocationWrapper wrapper = Mapper.mapToWrapper(location);
		String jsonString = JsonUtil.mapToJson(wrapper);
		try (OutputStream os = connection.getOutputStream()) {
			os.write(jsonString.getBytes());
			os.flush();
		} catch (Exception e) {
			// TODO: Add logger
			return ErrorHandler.handle(e, connection);
		}
		try (BufferedReader reader = RestApiUtil.getReader(connection)) {
			String resultString = reader.readLine();
			MSResultMessage<Boolean> infectedResult = Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return infectedResult;
		} catch (Exception e) {
			// TODO: Add logger
			return ErrorHandler.handle(e, connection);
		}
	}
}
