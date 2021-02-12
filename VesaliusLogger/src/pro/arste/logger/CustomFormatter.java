package pro.arste.logger;

import java.sql.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {

	private static final String LOG_FIELD_SEPARATOR = " :: ";

	@Override
	public String format(LogRecord record) {
		Throwable t = record.getThrown();
		Throwable cause = t.getCause();
		return "****Thread ID: " + record.getThreadID() + LOG_FIELD_SEPARATOR + t.getClass() + System.lineSeparator()
				+ getMethodDetails(cause) + "		Time: " + new Date(record.getMillis()) + System.lineSeparator()
				+ "		Message: " + t.getMessage() + System.lineSeparator();
	}

	public String getMethodDetails(Throwable cause) {
		if (cause == null)
			return "";
		StackTraceElement e = cause.getStackTrace()[0];
		return "			Class: " + e.getClassName() + LOG_FIELD_SEPARATOR + "Method: " + e.getMethodName()
				+ LOG_FIELD_SEPARATOR + "Line: " + e.getLineNumber() + LOG_FIELD_SEPARATOR + System.lineSeparator();
	}
}
