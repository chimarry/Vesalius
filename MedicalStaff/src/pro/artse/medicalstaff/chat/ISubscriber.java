package pro.artse.medicalstaff.chat;

public interface ISubscriber {
	void notify(String message);

	void notifyMedicalStaff(String message);
}
