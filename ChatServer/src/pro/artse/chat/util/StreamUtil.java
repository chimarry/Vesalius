package pro.artse.chat.util;

import java.io.*;

import javax.net.ssl.SSLSocket;

public class StreamUtil {

	public static BufferedReader getReader(SSLSocket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public static PrintWriter getWriter(SSLSocket socket) throws IOException {
		return new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}
}
