package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.managers.INotificationManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.models.NotificationWrapper;

@Path("notifications")
public class NotificationService extends BaseService {

	private final INotificationManager notificationManager = ManagerFactory.getNotificationManager();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNewerNotifications(@HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<List<NotificationWrapper>> notifications = notificationManager.getNewerNotifications(token);
			return notifications.buildResponse();
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}
}
