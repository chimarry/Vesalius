package pro.artse.dal.models;

/**
 * Class that represents public information about the user.
 */
public class KeyUserInfoDTO {
	private static final String SEPARATOR = "#";

	/**
	 * Token that is related to this user.
	 */
	private String token;

	/**
	 * Type of a person (infected, potentially infected, not infected).
	 */
	private int personType;

	/**
	 * Creates basic user's information, where all the values accept a token has
	 * default value.
	 * 
	 * @param token UserDTO's token.
	 */

	public KeyUserInfoDTO(String token, int personType) {
		super();
		this.token = token;
		this.personType = personType;
	}

	public KeyUserInfoDTO(String line) {
		String[] values = line.split(SEPARATOR);
		this.token = values[0];
		this.personType = Integer.parseInt(values[1]);
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

	@Override
	public String toString() {
		return token + SEPARATOR + personType;
	}
}
