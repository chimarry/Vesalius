package pro.artse.user.centralr.services;

import java.io.IOException;
import java.util.List;

import pro.artse.user.errorhandling.SUResultMessage;
import pro.artse.user.models.Notification;

public interface INotificationService {
	SUResultMessage<Notification[]> getNewerNotifications(String token) throws IOException;
}
