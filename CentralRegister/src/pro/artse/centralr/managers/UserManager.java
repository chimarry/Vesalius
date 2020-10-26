package pro.artse.centralr.managers;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.artse.dal.factory.ManagerFactory;

public class UserManager implements IUserManager {
	private final pro.artse.dal.managers.IUserManager userManager = ManagerFactory.getUserManager();

	@Override
	public ResultMessage<Boolean> unregister(String token) {
		Boolean isDeactived = userManager.deactivate(token);
		return new ResultMessage<Boolean>(isDeactived, OperationStatus.SUCCESS);
	}
}
