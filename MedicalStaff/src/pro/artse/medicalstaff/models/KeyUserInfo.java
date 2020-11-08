package pro.artse.medicalstaff.models;

import java.io.Serializable;

public class KeyUserInfo implements Serializable {

	private String token;

	// TODO: Maybe use CovidStatus - nicer
	private int personType;

	private int isBlocked;

	public KeyUserInfo(String token, int status) {
		this();
		setToken(token);
		setPersonType(status);
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

	public int getPersonType() {
		return personType;
	}

	public String getCovidStatus() {
		switch (personType) {
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

	public void setPersonType(int personType) {
		this.personType = personType;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof KeyUserInfo && personType == ((KeyUserInfo) obj).getPersonType();
	}
}
