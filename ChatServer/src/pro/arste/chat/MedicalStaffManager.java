package pro.arste.chat;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import pro.artse.chat.models.MedicalStaffMember;

public class MedicalStaffManager implements IMedicalStaffManager {

	private static Queue<Socket> medicalStaff = new PriorityBlockingQueue<Socket>();

	public MedicalStaffManager() {
		medicalStaff = new LinkedList<Socket>();
	}

	@Override
	public void makeAvailable(Socket member) {
		medicalStaff.add(member);
	}

	@Override
	public Socket getAvailable() {
		if (medicalStaff.isEmpty())
			return null;
		return medicalStaff.poll();
	}

}
