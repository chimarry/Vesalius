package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.LocationWrapper;
import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Location;
import pro.artse.user.util.RestApiUtil;
import pro.artse.user.util.json.JsonUtil;

public class LocationService implements ILocationService {

	@Override
	public SUResultMessage<Boolean> add(Location location, String token) throws IOException {
		// TODO: Add error mechanism
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.POST_LOCATION, "POST", true);
		connection.setRequestProperty("Content-Type", "application/json");
		LocationWrapper wrapper = pro.artse.user.util.Mapper.mapToWrapper(location);
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
			System.out.println(resultString);
			SUResultMessage<Boolean> locationResult = pro.artse.user.util.Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return locationResult;
		} catch (Exception e) {
			// TODO: Add logger
			return ErrorHandler.handle(e, connection);
		}
	}

	@Override
	public SUResultMessage<Location[]> getAll(String token, int days) throws IOException {
		StringBuilder pathBuilder = new StringBuilder(ApiPaths.GET_LOCATIONS);
		pathBuilder.append("?days=" + days);
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, pathBuilder.toString(), "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			SUResultMessage<Location[]> resultLocations = pro.artse.user.util.Mapper.mapFromCR(resultString,
					Location[].class);
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
