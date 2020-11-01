package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.managers.IActivityLogManager;
import pro.artse.centralr.managers.IAuthorizationManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.ActivityLogWrapper;

@Path("activities")
public class ActivityLogService extends BaseService {
	private final IActivityLogManager activitylogManger = ManagerFactory.getActivityLogManager();

	/**
	 * Adds new activity, if user has privilege to do so.
	 * 
	 * @param newActivity Data to be added.
	 * @param token       User's identifier.
	 * @return Wrapper result with correct HTTP status code. @see HttpResultMessage
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(ActivityLogWrapper newActivity, @HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<Boolean> resultMessage = activitylogManger.add(newActivity, token);
			return resultMessage.buildResponse();
		} catch (Exception e) {
			return ErrorHandler.handle(e).buildResponse();
		}
	}

	/**
	 * Returns list of all activities that belong to one user, specified by token.
	 * 
	 * @param token Unique identifier for the user.
	 * @return List of activities.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<List<ActivityLogWrapper>> activities = activitylogManger.getAll(token);
			return activities.buildResponse();
		} catch (Exception e) {
			return ErrorHandler.handle(e).buildResponse();
		}
	}
}
