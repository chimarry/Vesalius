package pro.arste.chat;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.net.ssl.SSLSocket;

public class MedicalStaffManager implements IMedicalStaffManager {

	private static Queue<SSLSocket> medicalStaff = new PriorityBlockingQueue<SSLSocket>();

	public MedicalStaffManager() {
		medicalStaff = new LinkedList<SSLSocket>();
	}

	@Override
	public void makeAvailable(SSLSocket member) {
		medicalStaff.add(member);
	}

	@Override
	public SSLSocket getAvailable() {
		if (medicalStaff.isEmpty())
			return null;
		SSLSocket socket = medicalStaff.poll();
		if (socket.isClosed())
			return null;
		return socket;
	}

}
