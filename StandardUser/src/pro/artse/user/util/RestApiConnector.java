package pro.artse.user.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestApiConnector {
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
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static BufferedReader getReader(HttpURLConnection connection) throws IOException {
		return new BufferedReader(new InputStreamReader((connection.getInputStream())));
	}
}
