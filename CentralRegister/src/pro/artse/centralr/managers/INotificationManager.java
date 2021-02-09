package pro.artse.centralr.managers;

import java.util.List;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.NotificationWrapper;

public interface INotificationManager {
	CrResultMessage<Boolean> add(NotificationWrapper notification);

	CrResultMessage<List<NotificationWrapper>> getNewerNotifications(String token);
}
