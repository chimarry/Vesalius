/**
 * 
 */
package pro.artse.dal.models;

/**
 * @author Marija
 * 
 *         Represents information about user that are kept in storage.
 */
public class User {
	/**
	 * Represents first name of the user. If user has many names, they should be
	 * concatenated with a dash.
	 */
	private String firstName;

	/**
	 * Represents last name of the user. If user has many last names, they should be
	 * concatenated with a dash.
	 */
	private String lastName;

	/**
	 * Represents unique birth identifier of a user.
	 */
	private String UBN;

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
	 * @return First name of an user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName First name of the user.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Last name of an user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName Last name of the user.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Unique birth identifier of an user.
	 */
	public String getUBN() {
		return UBN;
	}

	/**
	 * Sets unique birth identifier of an user.
	 * 
	 * @param ubn Unique birth identifier of the user.
	 */
	public void setUBN(String ubn) {
		UBN = ubn;
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
