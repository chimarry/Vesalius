package pro.artse.user.managers;

import pro.artse.user.models.User;

public interface ILoginManager {
	User getCurrentUser();
	boolean login(String token, String password);
	boolean saveUser(String token, String password);
	void logout();
	boolean removeUser();
}
