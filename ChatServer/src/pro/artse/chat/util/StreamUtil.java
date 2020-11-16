package pro.artse.chat.util;

import java.io.*;
import java.net.Socket;

public class StreamUtil {

	public static BufferedReader getReader(Socket socket) throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public static PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}
}
