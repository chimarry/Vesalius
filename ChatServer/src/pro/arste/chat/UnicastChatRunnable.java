package pro.arste.chat;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import pro.artse.chat.util.ConfigurationUtil;
import pro.artse.chat.util.StreamUtil;

public class UnicastChatRunnable implements Runnable {

	private Socket standardUserSocket;
	private Socket medicalStaffSocket;

	private volatile Boolean isFinished = false;

	public UnicastChatRunnable(Socket firstSocket, Socket secondSocket) {
		this.medicalStaffSocket = secondSocket;
		this.standardUserSocket = firstSocket;
	}

	@Override
	public void run() {
		System.out.println(standardUserSocket);
		System.out.println(medicalStaffSocket);
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
						System.out.println("SU: " + message);
						msPrintWriter.println(message);
						if (isFlag(message))
							isFinished = true;
					} catch (Exception e) {
						isFinished = true;
						msPrintWriter.println(ConfigurationUtil.get("errorFlag"));
						e.printStackTrace();
					}
				}
				System.out.println("Channel 1 is finished");
			});

			// Channel 2
			Thread channel2 = new Thread(() -> {
				String message = "";
				while (!isFinished) {
					try {
						message = msBufferedReader.readLine();
						System.out.println("MS: " + message);
						suPrintWriter.println(message);
						if (isFlag(message))
							isFinished = true;
					} catch (Exception e) {
						isFinished = true;
						suPrintWriter.println(ConfigurationUtil.get("errorFlag"));
						e.printStackTrace();
					}
				}
				System.out.println("Channel 2 is finished");
			});

			channel1.start();
			channel2.start();
			channel1.join();
			channel2.join();
			closeSockets(suPrintWriter, msPrintWriter);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			if (!medicalStaffSocket.isClosed()) {
				medicalStaffSocket.close();
				System.out.println("Medical staff is closed. " + LocalDateTime.now());
			}
			if (!standardUserSocket.isClosed()) {
				standardUserSocket.close();
				System.out.println("Standard user is closed. " + LocalDateTime.now());
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error when closing");
			e.printStackTrace();
		}
	}
}