package pro.artse.medicalstaff.models;

import java.io.Serializable;

public class KeyUserInfo implements Serializable {

	private String token;

	// TODO: Maybe use CovidStatus - nicer
	private int covidStatus;

	private int isBlocked;

	public KeyUserInfo(String token, int status) {
		this();
		setToken(token);
		setCovidStatus(status);
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

	public int getCovidStatus() {
		return covidStatus;
	}

	public String getCovidStatusName() {
		switch (covidStatus) {
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

	public void setCovidStatus(int covidStatus) {
		this.covidStatus = covidStatus;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof KeyUserInfo && covidStatus == ((KeyUserInfo) obj).getCovidStatus();
	}
}
