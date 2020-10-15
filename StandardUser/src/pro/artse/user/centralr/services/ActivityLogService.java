package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.user.models.ActivityLog;
import pro.artse.user.util.RestApiUtil;
import pro.artse.user.util.json.JsonUtil;

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
			ArrayList<ActivityLog> activities = JsonUtil.deserialize(JsonUtil.readJsonList(bufferedReader),
					ActivityLog.class);
			connection.disconnect();
			return activities;
		} catch (IOException e) {
			connection.disconnect();
			throw e;
		}
	}
}
