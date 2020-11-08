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
	private int covidStatus;

	/**
	 * Did medical staff block user? 0 - no, 1 - yes
	 */
	private int isBlocked;

	/**
	 * Creates basic user's information, where all the values accept a token has
	 * default value.
	 * 
	 * @param token UserDTO's token.
	 */

	public static KeyUserInfoDTO parse(String line) {
		String[] values = line.split(SEPARATOR);
		KeyUserInfoDTO userInfoDTO = new KeyUserInfoDTO(values[0]);
		userInfoDTO.setCovidStatus(Integer.parseInt(values[1]));
		userInfoDTO.setIsBlocked(0);
		return userInfoDTO;
	}

	public KeyUserInfoDTO(String token) {
		super();
		this.token = token;
		this.covidStatus = 0;
		this.isBlocked = 0;
	}

	public void setIsBlocked(int blocked) {
		isBlocked = blocked > 0 ? 1 : 0;
	}

	public int getIsBlocked() {
		return isBlocked;
	}

	public boolean isBlocked() {
		return isBlocked == 0 ? false : true;
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
	public void setCovidStatus(int personType) {
		this.covidStatus = personType;
	}

	@Override
	public String toString() {
		return token + SEPARATOR + covidStatus;
	}
}
