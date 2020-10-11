import java.lang.reflect.InvocationTargetException;

import javax.xml.rpc.ServiceException;

import com.google.gson.Gson;
import com.sun.jndi.url.dns.dnsURLContext;

import pro.arste.common.result.OperationStatus;
import pro.arste.common.result.ResultMessage;
import pro.arste.tokenserver.services.TokenService;
import pro.arste.tokenserver.services.TokenServiceServiceLocator;

public class TestClient {

	public static void main(String[] args) {
		TokenServiceServiceLocator locator = new TokenServiceServiceLocator();
		try {
			TokenService service = locator.getTokenService();
			String tokenString = service.generateToken("Marijijca", "Vaske", "Vanja");
			ResultMessage<String> message = new Gson().fromJson(tokenString,
					new ResultMessage<String>(OperationStatus.SUCCESS).getClass());
			System.out.println(tokenString);
			System.out.print("Rezultat" + message.getResult());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.getCause();
			e.printStackTrace();
		}
	}
}