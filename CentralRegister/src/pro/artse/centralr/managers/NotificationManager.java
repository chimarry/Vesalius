package pro.artse.centralr.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.NotificationWrapper;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.models.NotificationDTO;

public class NotificationManager implements INotificationManager {

	private final pro.artse.dal.managers.INotificationManager notificationManager = ManagerFactory
			.getNotificationManager();

	@Override
	public CrResultMessage<Boolean> add(NotificationWrapper notification) {
		DBResultMessage<Boolean> isAdded = notificationManager.addNotification(Mapper.mapToDTO(notification));
		return Mapper.mapFrom(isAdded);
	}

	@Override
	public CrResultMessage<List<NotificationWrapper>> getNewerNotifications(String token) {
		DBResultMessage<List<NotificationDTO>> notifications = notificationManager.getNewerNotifications(token);
		List<NotificationWrapper> wrappedNotifications = notifications.isSuccess() ? notifications.getResult().stream()
				.map(x -> Mapper.mapToWrapper(x)).collect(Collectors.toCollection(ArrayList<NotificationWrapper>::new))
				: new ArrayList<>();
		return new CrResultMessage<List<NotificationWrapper>>(wrappedNotifications,
				Mapper.mapStatus(notifications.getStatus()), notifications.getMessage());
	}

}
