package pro.arste.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VesaliusLogger implements IVesaliusLogger {
	private Logger logger;
	private Handler handler;

	public VesaliusLogger(String fileName) {
		logger = Logger.getLogger(VesaliusLogger.class.getName());
		logger.setLevel(Level.ALL);
		// FileHandler file name with max size and number of log files limit
		try {
			handler = new FileHandler(fileName, 2000, 20);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler.setFormatter(new CustomFormatter());
		// setting custom filter for FileHandler
		logger.addHandler(handler);
	}

	@Override
	public void log(Exception exception) {
		logger.log(Level.SEVERE, "Exception!", exception);
	}

	@Override
	public void log(Level level, String message) {
		logger.log(level, message);
	}
}
