package pro.artse.centralr.models;

public class KeyUserInfoWrapper {
	/**
	 * Token that is related to this user.
	 */
	private String token;

	/**
	 * Type of a person (infected, potentially infected, not infected).
	 */
	private int personType;

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

	public KeyUserInfoWrapper(String token, int personType) {
		super();
		this.token = token;
		this.personType = personType;
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
	public int getPersonType() {
		return personType;
	}

	/**
	 * Sets type of a person.
	 * 
	 * @param personType Number that indicates what type of a person does user
	 *                   belong to.
	 */
	public void setPersonType(int personType) {
		this.personType = personType;
	}

	public int getIsBloked() {
		return isBloked;
	}

	public void setIsBloked(int isBloked) {
		this.isBloked = isBloked;
	}
}
