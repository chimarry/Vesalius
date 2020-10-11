import pro.artse.tokenserver.services.TokenService;

public class Test {

	public static void main(String[] args) {
		TokenService tokenService = new TokenService();
		String resultString = tokenService.generateToken("Dijana", "Novakovic", "123123131");
		System.out.println(resultString);
	}
}
