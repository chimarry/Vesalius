package pro.artse.user.centralr.services;

import pro.artse.user.errorhandling.SUResultMessage;

public interface IUserService {
	SUResultMessage<Boolean> unregister(String token);
}
