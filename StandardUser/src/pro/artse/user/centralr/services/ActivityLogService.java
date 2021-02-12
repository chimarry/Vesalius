package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import pro.artse.centralr.api.ApiPaths;
import pro.artse.centralr.models.ActivityLogWrapper;
import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
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
	public SUResultMessage<ActivityLog[]> getAll(String token) throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.GET_ALL_ACTIVITIES, "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			SUResultMessage<ActivityLog[]> resultActivities = pro.artse.user.util.Mapper.mapFromCR(resultString,
					ActivityLog[].class);
			connection.disconnect();
			return resultActivities;
		} catch (IOException e) {
			ErrorHandler.handle(e, connection);
			throw e;
		} catch (Exception e) {
			ErrorHandler.handle(e, connection);
			throw e;
		}
	}

	@Override
	public SUResultMessage<Boolean> add(ActivityLog activityLog, String token) throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.POST_ACTIVITY, "POST", true);
		connection.setRequestProperty("Content-Type", "application/json");
		ActivityLogWrapper wrapper = pro.artse.user.util.Mapper.mapToWrapper(activityLog);
		String jsonString = JsonUtil.mapToJson(wrapper);

		try (OutputStream os = connection.getOutputStream()) {
			os.write(jsonString.getBytes());
			os.flush();
		} catch (Exception e) {
			return ErrorHandler.handle(e, connection);
		}
		try (BufferedReader reader = RestApiUtil.getReader(connection)) {
			String resultString = reader.readLine();
			System.out.println(resultString);
			SUResultMessage<Boolean> activityResult = pro.artse.user.util.Mapper.mapFromCR(resultString, Boolean.class);
			connection.disconnect();
			return activityResult;
		} catch (Exception e) {
			return ErrorHandler.handle(e, connection);
		}
	}
}
