package pro.artse.medicalstaff.models;

import java.io.Serializable;

public class KeyUserInfo implements Serializable {

	private String token;

	private CovidStatus status;

	public KeyUserInfo(String token, CovidStatus status) {
		this();
		this.token = token;
		this.status = status;
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

	public CovidStatus getStatus() {
		return status;
	}

	public void setStatus(CovidStatus status) {
		this.status = status;
	}

}
