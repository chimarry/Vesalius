package pro.artse.medicalstaff.models;

import java.io.Serializable;

public class KeyUserInfo implements Serializable {

	private String token;

	// TODO: Maybe use CovidStatus - nicer
	private int status;

	public KeyUserInfo(String token, int status) {
		this();
		setToken(token);
		setStatus(status);
	}

	public KeyUserInfo() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public String getCovidStatus() {
		switch (status) {
		case 0:
			return "Not infected";
		case 1:
			return "Infected";
		case 2:
			return "Potentially infected";
		default:
			return "Unknown";
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
