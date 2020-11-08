package pro.artse.centralr.models;

public class KeyUserInfoWrapper {
	/**
	 * Token that is related to this user.
	 */
	private String token;

	/**
	 * Type of a person (infected, potentially infected, not infected).
	 */
	private int covidStatus;

	/**
	 * Did medical staff block this user?
	 */
	private int isBloked;

	/**
	 * Creates basic user's information, where all the values accept a token has
	 * default value.
	 * 
	 * @param token UserDTO's token.
	 */

	public KeyUserInfoWrapper(String token, int covidStatus) {
		super();
		this.token = token;
		this.covidStatus = covidStatus;
		this.isBloked = 0;
	}

	/**
	 * @return Token of an user.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets token for an user.
	 * 
	 * @param token UserDTO's token.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return Number that indicates what type of a person does user belong to.
	 */
	public int getCovidStatus() {
		return covidStatus;
	}

	/**
	 * Sets type of a person.
	 * 
	 * @param covidStatus Number that indicates what type of a person does user
	 *                   belong to.
	 */
	public void setCovidStatus(int covidStatus) {
		this.covidStatus = covidStatus;
	}

	public int getIsBloked() {
		return isBloked;
	}

	public void setIsBloked(int isBloked) {
		this.isBloked = isBloked;
	}
}
