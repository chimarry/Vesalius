package pro.arste.logger;

import java.util.logging.Level;

public interface IVesaliusLogger {
	void log(Exception exception);

	void log(Level level, String message);
}
