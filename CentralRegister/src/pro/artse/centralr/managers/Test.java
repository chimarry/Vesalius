package pro.artse.centralr.managers;

import java.time.LocalDateTime;

import pro.arste.centralr.errorhandling.CrResultMessage;
import pro.artse.centralr.models.LocationWrapper;

public class Test {

	public static void main(String[] args) {
		LocationWrapper locationWrapper = new LocationWrapper(17.191052994189608, 44.77777556721956,
				LocalDateTime.of(2021, 2, 1, 12, 0), LocalDateTime.of(2021, 2, 1, 13, 0));
		CrResultMessage<Boolean> infected = new UserManager().markUserAsInfected("O1KP2UdHQ7y3pu4Zqj6wgw==",
				locationWrapper);
		System.out.println(infected.getResult());
	}

}
