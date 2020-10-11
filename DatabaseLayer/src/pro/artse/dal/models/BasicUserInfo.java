package pro.artse.dal.models;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;

/**
 * Class that represents public information about the user.
 */
public class BasicUserInfo {

	/**
	 * Token that is related to this user.
	 */
	private String token;

	/**
	 * Flag that indicates whether the user is active or not.
	 */
	private boolean isDeactivated;

	/**
	 * Type of a person (infected, potentially infected, not infected).
	 */
	private int personType;
	
	
	/**
	 * Creates basic user's information, where all the values accept a token has default value.
	 * @param token User's token.
	 */

	public BasicUserInfo(String token) {
		super();
		this.token = token;
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
	 * @param token User's token.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return Number that indicates about status of user's token.
	 */
	public int isDeactivatedInt() {
		return isDeactivated ? 1 : 0;
	}

	/**
	 * @return Flag that indicates about status of user's token.
	 */
	public boolean isDeactivated() {
		return isDeactivated;
	}

	/**
	 * Sets flag that indicates whether user's token has been deactivated.
	 * 
	 * @param isDeactivated Has user's token been deactivated?
	 */
	public void setDeactivated(boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
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
}
