package pro.artse.centralr.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.json.JsonUtil;

import pro.arste.common.result.ResultMessage;
import pro.artse.centralr.managers.IAuthorizationManager;
import pro.artse.centralr.managers.IUserManager;
import pro.artse.centralr.managers.ManagerFactory;

@Path("users")
public class UserService {
	private final IUserManager userManager = ManagerFactory.getUserManager();
	private final IAuthorizationManager authorizationManager = ManagerFactory.getAuthorizationManager();

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response unregister(@HeaderParam("token") String token) {
		if (!authorizationManager.authorize(token))
			return HttpResultMessage.unauthorized();
		ResultMessage<Boolean> isUnregistered = userManager.unregister(token);
		return HttpResultMessage.getResponse(isUnregistered);
	}
}
