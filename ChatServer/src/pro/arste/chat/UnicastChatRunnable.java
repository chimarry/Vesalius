package pro.arste.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

import com.sun.swing.internal.plaf.synth.resources.synth_zh_TW;

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
		openSendConnection();
	}

	public void openSendConnection() {
		System.out.println(standardUserSocket);
		System.out.println(medicalStaffSocket);

		// Channel 1
		Thread channel1 = new Thread(() -> {
			BufferedReader bufferedReader = null;
			PrintWriter printWriter = null;
			try {
				bufferedReader = StreamUtil.getReader(standardUserSocket);
				printWriter = StreamUtil.getWriter(medicalStaffSocket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String message = "";
			while (!isFinished) {
				try {
					message = bufferedReader.readLine();
					printWriter.println(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					closeSockets();
				}
			}
		});

		// Channel 2
		Thread channel2 = new Thread(() -> {
			BufferedReader bufferedReader = null;
			PrintWriter printWriter = null;
			try {
				bufferedReader = StreamUtil.getReader(medicalStaffSocket);
				printWriter = StreamUtil.getWriter(standardUserSocket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String message = "";
			while (!isFinished) {
				try {
					message = bufferedReader.readLine();
					printWriter.println(message);
					if (message.equals(ConfigurationUtil.get("endFlag"))) {
						isFinished = true;
						closeSockets();
					}
				} catch (IOException e) {
					closeSockets();
				}
			}
		});
		channel1.start();
		channel2.start();
	}

	private synchronized void closeSockets() {
		try {
			if (!medicalStaffSocket.isClosed()) {
				medicalStaffSocket.close();
				System.out.println("Medical staff is closed. " + LocalDate.now());
			}
			if (!standardUserSocket.isClosed()) {
				standardUserSocket.close();
				System.out.println("Standard user is closed. " + LocalDate.now());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error when closing");
			e.printStackTrace();
		}
	}
}