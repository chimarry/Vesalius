package pro.artse.centralr.managers;

import pro.arste.common.result.ResultMessage;

public interface IUserManager {
	ResultMessage<Boolean> unregister(String token);
}
