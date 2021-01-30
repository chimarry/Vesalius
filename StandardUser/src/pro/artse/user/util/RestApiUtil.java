package pro.artse.user.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
	public static HttpURLConnection openConnectionJSON(String token, String path, String method, boolean doOutput) {
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(doOutput);
			connection.setRequestMethod(method);
			connection.setRequestProperty("token", token);
			return connection;
		} catch (FileNotFoundException e) {
			// TODO: Add logger
			return null;
		} catch (IOException e) {
			// TODO: Add logger
			e.printStackTrace();
			return null;
		}
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
