package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.managers.ILocationManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.LocationWrapper;

@Path("locations")
public class LocationService extends BaseService {
	private final ILocationManager locationManager = ManagerFactory.getLocationManager();

	/**
	 * Adds user location with time information, if an user has a privilege to do
	 * so.
	 * 
	 * @param newLocation Data to be added.
	 * @param token       UserDTO's identifier.
	 * @return Wrapper result with correct HTTP status code. @see HttpResultMessage
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(LocationWrapper newLocation, @HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<Boolean> resultMessage = locationManager.saveLocation(token, newLocation);
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
	public Response getAll(@QueryParam("days") int days, @HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<List<LocationWrapper>> locations = locationManager.getAll(token, days);
			return locations.buildResponse();
		} catch (Exception e) {
			return ErrorHandler.handle(e).buildResponse();
		}
	}
}
