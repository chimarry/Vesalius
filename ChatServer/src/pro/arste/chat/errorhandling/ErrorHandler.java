package pro.arste.chat.errorhandling;

import pro.arste.logger.IVesaliusLogger;
import pro.arste.logger.LoggerFactory;

public class ErrorHandler {
	private static final String OUTPUT_LOG_FILE = "chat_logs.log";
	private static final IVesaliusLogger logger = LoggerFactory.getLogger(OUTPUT_LOG_FILE);

	public static void handle(Exception ex) {
		logger.log(ex);
	}
}
