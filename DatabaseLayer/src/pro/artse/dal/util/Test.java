package pro.artse.dal.util;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.factory.ManagerFactory;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.UserDTO;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IUserManager userManager = ManagerFactory.getUserManager();
		KeyUserInfoDTO userIndo = new KeyUserInfoDTO("asdafafa312313a");
		DBResultMessage<Boolean> isAdded =  userManager.add(new UserDTO(userIndo,"Vanja", "Novakovic","1232131312"));
		System.out.println(isAdded.getStatus());
	}
}
