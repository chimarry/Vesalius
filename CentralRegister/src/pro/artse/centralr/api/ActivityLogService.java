package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.common.result.ResultMessage;
import pro.artse.centralr.api.HttpResultMessage.OperationType;
import pro.artse.centralr.managers.IActivityLogManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.ActivityLogWrapper;

@Path("activities")
public class ActivityLogService {
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
	public Response Add(ActivityLogWrapper newActivity, @HeaderParam("token") String token) {
		// TODO: Add token validation
		ResultMessage<Boolean> resultMessage = activitylogManger.add(newActivity, token);
		return HttpResultMessage.GetResponse(resultMessage, OperationType.CREATE);
	}

	/**
	 * Returns list of all activities that belong to one user, specified by token.
	 * 
	 * @param token Unique identifier for the user.
	 * @return List of activities.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetAll(@HeaderParam("token") String token) {
		// TODO: Add token validation
		List<ActivityLogWrapper> activities = activitylogManger.getAll(token);
		return HttpResultMessage.GetResponse(activities);
	}
}
