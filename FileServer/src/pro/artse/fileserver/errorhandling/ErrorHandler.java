package pro.artse.fileserver.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class ErrorHandler {
	public static <T> FSResultMessage<T> handle(Exception ex, T result) {
		FSResultMessage<T> resultMessage = handle(ex);
		resultMessage.setResult(result);
		return resultMessage;
	}

	public static <T> FSResultMessage<T> handle(Exception ex) {
		// TODO: Use logger
		ex.printStackTrace();
		if (ex instanceof DataFormatException)
			return new FSResultMessage<T>(null, FSStatus.INVALID_DATA, "File is not properly compressed.");
		else if (ex instanceof FileNotFoundException)
			return new FSResultMessage<T>(null, FSStatus.NOT_FOUND, "File not found.");
		if (ex instanceof IOException)
			return new FSResultMessage<T>(null, FSStatus.SERVER_ERROR, "Error working with I/O.");
		return new FSResultMessage<T>(null, FSStatus.UNKNOWN_ERROR, "Exception occured.");
	}
}
