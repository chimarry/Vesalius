package pro.arste.logger;

public final class LoggerFactory {
	public static IVesaliusLogger getLogger(String fileName) {
		return new VesaliusLogger(fileName);
	}
}
