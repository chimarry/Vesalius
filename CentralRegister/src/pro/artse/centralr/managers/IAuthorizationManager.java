package pro.artse.centralr.managers;

import pro.arste.centralr.errorhandling.CrResultMessage;

public interface IAuthorizationManager {
	CrResultMessage<Boolean> authorize(String token);
}
