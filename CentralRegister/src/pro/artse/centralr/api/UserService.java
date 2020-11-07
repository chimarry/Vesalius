package pro.artse.centralr.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.arste.centralr.errorhandling.ErrorHandler;
import pro.artse.centralr.managers.IUserManager;
import pro.artse.centralr.managers.ManagerFactory;
import pro.artse.centralr.models.KeyUserInfoWrapper;

@Path("users")
public class UserService extends BaseService {
	private final IUserManager userManager = ManagerFactory.getUserManager();

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response unregister(@HeaderParam("token") String token) {
		try {
			authorize(token);
			CrResultMessage<Boolean> isUnregistered = userManager.unregister(token);
			return isUnregistered.buildResponse();
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
}
