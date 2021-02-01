package pro.artse.centralr.api;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.managers.ILocationManager;
import pro.artse.centralr.managers.IUserManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.KeyUserInfoWrapper;
import pro.artse.centralr.models.LocationWrapper;

@Path("users")
public class UserService extends BaseService {
	private final IUserManager userManager = ManagerFactory.getUserManager();
	private final ILocationManager locationManager = ManagerFactory.getLocationManager();

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response unregister(@HeaderParam("token") String token) {
		try {
			// TODO: Add logic
			authorize(token);
			return null;
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		try {
			CrResultMessage<KeyUserInfoWrapper[]> tokens = userManager.getAll();
			return tokens.buildResponse();
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}

	@GET
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("token") String token) {
		try {
			CrResultMessage<KeyUserInfoWrapper> user = userManager.search(token);
			return user.buildResponse();
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}

	@DELETE
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response blockUser(@PathParam("token") String token) {
		try {
			CrResultMessage<Boolean> user = userManager.blockUser(token);
			return user.buildResponse();
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}

	@GET
	@Path("/{token}/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocations(@QueryParam("days") int days, @PathParam("token") String token) {
		try {
			CrResultMessage<List<LocationWrapper>> locations = locationManager.getAll(token, days);
			return locations.buildResponse();
		} catch (Exception ex) {
			return ErrorHandler.handle(ex).buildResponse();
		}
	}

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
	@Path("/{token}/infected")
	public Response markAsInfected(LocationWrapper location, @PathParam("token") String token) {
		try {
			CrResultMessage<Boolean> resultMessage = userManager.markUserAsInfected(token, location);
			return resultMessage.buildResponse();
		} catch (Exception e) {
			return ErrorHandler.handle(e).buildResponse();
		}
	}
}
