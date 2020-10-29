package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.ws.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.errorhandling.SUStatus;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.util.RestApiUtil;
import pro.artse.user.util.json.JsonUtil;
import sun.management.counter.Variability;

public class ActivityLogService implements IActivityLogService {

	/**
	 * Gets list of user activities using HTTP connection (Rest).
	 * 
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<ActivityLog> getAll(String token) throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.GET_ALL_ACTIVITIES, "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			SUResultMessage<ActivityLog[]> resultActivities = pro.artse.user.util.Mapper.mapFromCR(resultString,
					ActivityLog[].class);
			connection.disconnect();
			return Arrays.asList(resultActivities.getResult());
		} catch (IOException e) {
			e.printStackTrace();
			connection.disconnect();
			throw e;
		} catch (Exception e) {
			connection.disconnect();
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public SUResultMessage<Boolean> add(ActivityLog activityLog, String token) throws IOException {
		// TODO: Add error mechanism
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.POST_ACTIVITY, "POST", true);
		connection.setRequestProperty("Content-Type", "application/json");
		ActivityLogWrapper wrapper = pro.artse.user.util.Mapper.mapToWrapper(activityLog);
		String jsonString = JsonUtil.mapToJson(wrapper);
		try (OutputStream os = connection.getOutputStream()) {
			os.write(jsonString.getBytes());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			connection.disconnect();
			return null;
		}
		try (BufferedReader reader = RestApiUtil.getReader(connection)) {
			String resultString = reader.readLine();
			System.out.println(resultString);
			SUResultMessage<Boolean> activityResult = pro.artse.user.util.Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return activityResult;
		} catch (Exception e) {
			e.printStackTrace();
			connection.disconnect();
			return null;
		}
	}
}
