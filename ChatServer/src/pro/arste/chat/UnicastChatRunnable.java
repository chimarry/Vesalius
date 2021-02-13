package pro.arste.chat;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

import pro.arste.chat.errorhandling.ErrorHandler;
import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;

public class UnicastChatRunnable implements Runnable {

	private SSLSocket standardUserSocket;
	private SSLSocket medicalStaffSocket;

	private volatile Boolean isFinished = false;

	public UnicastChatRunnable(SSLSocket firstSocket, SSLSocket secondSocket) {
		this.medicalStaffSocket = secondSocket;
		this.standardUserSocket = firstSocket;
	}

	@Override
	public void run() {
		try {
			BufferedReader suBufferedReader = StreamUtil.getReader(standardUserSocket);
			PrintWriter suPrintWriter = StreamUtil.getWriter(standardUserSocket);
			BufferedReader msBufferedReader = StreamUtil.getReader(medicalStaffSocket);
			PrintWriter msPrintWriter = StreamUtil.getWriter(medicalStaffSocket);

			// Channel 1
			Thread channel1 = new Thread(() -> {
				String message = "";
				while (!isFinished) {
					try {
						message = suBufferedReader.readLine();
						msPrintWriter.println(message);
						if (isFlag(message))
							isFinished = true;
					} catch (Exception e) {
						isFinished = true;
						msPrintWriter.println(ConfigurationUtil.get("errorFlag"));
						ErrorHandler.handle(e);
					}
				}
			});

			// Channel 2
			Thread channel2 = new Thread(() -> {
				String message = "";
				while (!isFinished) {
					try {
						message = msBufferedReader.readLine();
						suPrintWriter.println(message);
						if (isFlag(message))
							isFinished = true;
					} catch (Exception e) {
						isFinished = true;
						suPrintWriter.println(ConfigurationUtil.get("errorFlag"));
						ErrorHandler.handle(e);
					}
				}
			});

			channel1.start();
			channel2.start();
			channel1.join();
			channel2.join();
			closeSockets(suPrintWriter, msPrintWriter);
		} catch (Exception e1) {
			ErrorHandler.handle(e1);
		}

	}

	private Boolean isFlag(String message) {
		return message.equals(ConfigurationUtil.get("endFlag")) || message.equals(ConfigurationUtil.get("errorFlag"));
	}

	private void closeSockets(PrintWriter suPrintWriter, PrintWriter msPrintWriter) {
		try {
			isFinished = true;
			suPrintWriter.println(ConfigurationUtil.get("closeFlag"));
			msPrintWriter.println(ConfigurationUtil.get("closeFlag"));
			if (!medicalStaffSocket.isClosed())
				medicalStaffSocket.close();

			if (!standardUserSocket.isClosed())
				standardUserSocket.close();
		} catch (Exception e) {
			ErrorHandler.handle(e);
		}
	}
}