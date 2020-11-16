package pro.artse.chat.models;

public class MedicalStaffMember {
	private int port;
	private String ipAddress;

	public MedicalStaffMember() {

	}

	public MedicalStaffMember(int port, String ipAddress) {
		super();
		this.port = port;
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
