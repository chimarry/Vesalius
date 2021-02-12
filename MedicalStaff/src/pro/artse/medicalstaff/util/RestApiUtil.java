package pro.artse.medicalstaff.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.artse.medicalstaff.errorhandling.ErrorHandler;

public class RestApiUtil {
	public static int SUCCESS_HTTP_CODE_MAX = 299;

	/**
	 * Opens and configures HTTP URL connection.
	 * 
	 * @param token    Unique identifier for a user.
	 * @param path     Path to resource.
	 * @param method   HTTP method type.
	 * @param doOutput Output?
	 * @return Opened and configured HTTP URL connection.
	 */
	public static HttpURLConnection openConnectionJSON(String path, String method, boolean doOutput) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(doOutput);
			connection.setRequestMethod(method);
			return connection;
		} catch (FileNotFoundException | IllegalStateException e) {
			ErrorHandler.handle(e, connection);
			return null;
		} catch (IOException e) {
			ErrorHandler.handle(e, connection);
			return null;
		}
	}

	public static String buildPath(String path, Object... parameters) {
		StringBuilder buildPath = new StringBuilder(path);
		Pattern paramterPattern = Pattern.compile("\\{([a-zA-Z])*\\}");
		Matcher matcher = paramterPattern.matcher(path);
		int count = 0;
		while (matcher.find()) {
			String parameter = parameters[count].toString();
			buildPath.replace(matcher.start(), matcher.end(), parameter);
		}
		return buildPath.toString();
	}

	/**
	 * Opens reader towards resource, via http.
	 * 
	 * @param connection Opened HTTP URL connection
	 * @return Opened BufferedReader object.
	 * @throws IOException
	 */
	public static BufferedReader getReader(HttpURLConnection connection) throws IOException {
		return new BufferedReader(new InputStreamReader(
				(connection.getResponseCode() <= SUCCESS_HTTP_CODE_MAX ? connection.getInputStream()
						: connection.getErrorStream())));
	}
}
