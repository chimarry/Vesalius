package pro.artse.dal.managers;

import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.models.NotificationDTO;

public interface INotificationManager {
	DBResultMessage<Boolean> addNotification(NotificationDTO notification);

	DBResultMessage<List<NotificationDTO>> getNewerNotifications(String user);
}
