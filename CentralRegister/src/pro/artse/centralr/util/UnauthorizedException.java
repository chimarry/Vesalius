package pro.artse.centralr.util;

public class UnauthorizedException extends Exception {
	public UnauthorizedException() {
		super("You are not authorizated.");
	}
}
