package pro.artse.centralr.managers;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.util.Mapper;
import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;

public class UserManager implements IUserManager {
	private final pro.artse.dal.managers.IUserManager userManager = ManagerFactory.getUserManager();

	@Override
	public CrResultMessage<Boolean> unregister(String token) {
		DBResultMessage<Boolean> isDeactived = userManager.deactivate(token);
		return Mapper.mapFrom(isDeactived);
	}
}
