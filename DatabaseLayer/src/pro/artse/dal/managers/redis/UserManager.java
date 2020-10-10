package pro.artse.dal.managers.redis;

import java.util.List;

import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.User;

public class UserManager implements IUserManager {

	@Override
	public boolean add(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deactivate(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> GetAllTokens() {
		// TODO Auto-generated method stub
		return null;
	}

}
