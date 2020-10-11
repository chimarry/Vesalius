import java.lang.reflect.InvocationTargetException;

import javax.xml.rpc.ServiceException;

import com.sun.jndi.url.dns.dnsURLContext;

import pro.arste.tokenserver.services.TokenService;
import pro.arste.tokenserver.services.TokenServiceServiceLocator;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TokenServiceServiceLocator locator = new TokenServiceServiceLocator();
		try {
			TokenService service = locator.getTokenService();
			String tokenString = service.generateToken("Marijijca", "Vaske", "Vanja");
			System.out.println(tokenString);
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
