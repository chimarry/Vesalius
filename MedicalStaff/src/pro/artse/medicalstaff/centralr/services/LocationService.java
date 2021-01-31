package pro.artse.medicalstaff.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.medicalstaff.errorhandling.MSResultMessage;
import pro.artse.medicalstaff.models.Location;
import pro.artse.medicalstaff.util.Mapper;
import pro.artse.medicalstaff.util.RestApiUtil;

public class LocationService implements ILocationService {

	@Override
	public MSResultMessage<Location[]> getAll(String token, int days) throws IOException {
		String urlPath = RestApiUtil.buildPath(ApiPaths.GET_USER_LOCATIONS, token);
		StringBuilder pathBuilder = new StringBuilder(urlPath);
		pathBuilder.append("?days=" + days);
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(pathBuilder.toString(), "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			MSResultMessage<Location[]> resultLocations = Mapper.mapFromCR(resultString, Location[].class);
			connection.disconnect();
			return resultLocations;
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
