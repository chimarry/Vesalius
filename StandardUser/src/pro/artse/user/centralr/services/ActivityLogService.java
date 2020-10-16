package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.ws.Response;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.centralr.util.Mapper;
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

	@Override
	public boolean add(ActivityLog activityLog, String token) throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.POST_ACTIVITY, "POST", true);
		connection.setRequestProperty("Content-Type", "application/json");
		ActivityLogWrapper wrapper = pro.artse.user.util.Mapper.mapToWrapper(activityLog);
		String jsonString = JsonUtil.mapToJson(wrapper);// , JsonUtil.ACTIVITY_LOG_SERIALIZER,
														// ActivityLogWrapper.class);
		System.out.println(jsonString);
		System.out.println(token + "activities");
		try (OutputStream os = connection.getOutputStream()) {
			os.write(jsonString.getBytes());
			os.flush();
			int code = connection.getResponseCode();
			if (code != HttpURLConnection.HTTP_CREATED)
				return false;
			return true;
		}
	}
}
