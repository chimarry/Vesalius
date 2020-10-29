package pro.artse.centralr.managers;

import pro.arste.centralr.errorhandling.CrResultMessage;

public interface IUserManager {
	CrResultMessage<Boolean> unregister(String token);
}
