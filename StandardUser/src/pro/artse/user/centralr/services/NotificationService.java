package pro.artse.user.centralr.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import pro.artse.centralr.api.ApiPaths;
import pro.artse.user.errorhandling.ErrorHandler;
import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Notification;
import pro.artse.user.util.RestApiUtil;

public class NotificationService implements INotificationService {

	@Override
	public SUResultMessage<Notification[]> getNewerNotifications(String token) throws IOException {
		HttpURLConnection connection = RestApiUtil.openConnectionJSON(token, ApiPaths.GET_NOTIFICATIONS, "GET", false);
		try (BufferedReader bufferedReader = RestApiUtil.getReader(connection)) {
			String resultString = bufferedReader.readLine();
			SUResultMessage<Notification[]> resultNotifications = pro.artse.user.util.Mapper.mapFromCR(resultString,
					Notification[].class);
			connection.disconnect();
			return resultNotifications;
		} catch (IOException e) {
			ErrorHandler.handle(e, connection);
			throw e;
		} catch (Exception e) {
			ErrorHandler.handle(e, connection);
			throw e;
		}
	}

}
