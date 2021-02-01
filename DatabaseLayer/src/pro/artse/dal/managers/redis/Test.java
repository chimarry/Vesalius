package pro.artse.dal.managers.redis;

import java.time.LocalDateTime;
import java.util.List;

import pro.artse.dal.errorhandling.DBResultMessage;
import pro.artse.dal.managers.IUserManager;
import pro.artse.dal.models.KeyUserInfoDTO;
import pro.artse.dal.models.LocationDTO;
import pro.artse.dal.models.UserLocationDTO;

public class Test {

	public static void main(String[] args) {
		IUserManager userManager = new UserManager();
		List<KeyUserInfoDTO> users = userManager.getAllAllowedInformation().getResult();
		users.forEach(x -> System.out.println(x));
		/*
		 * 
		 * LocationDTO location = new LocationDTO(17.191052994189608, 44.77777556721956,
		 * LocalDateTime.of(2021, 2, 1, 12, 0), LocalDateTime.of(2021, 2, 1, 13, 0));
		 * String token = "O1KP2UdHQ7y3pu4Zqj6wgw==";
		 * DBResultMessage<List<UserLocationDTO>> user =
		 * userManager.markUsersAsPotentiallyInfected(token, location, 20000, 5);
		 * System.out.println(user.isSuccess());
		 */
	}
}
