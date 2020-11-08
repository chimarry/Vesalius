/**
 * 
 */
package pro.artse.dal.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marija
 * 
 *         Represents information about user that are kept in storage.
 */
public class UserDTO {

	/**
	 * Information about the user that can be displayed to the public.
	 */
	private KeyUserInfoDTO keyUserInfoDTO;

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
	 * Created user object with default values.
	 */
	public UserDTO() {

	}

	/**
	 * Creates user object.
	 * 
	 * @param keyUserInfoDTO Public information about the user.
	 * @param firstName      First name of the user.
	 * @param lastName       Last name of the user.
	 * @param uBN            Unique birth number of the user.
	 */
	public UserDTO(KeyUserInfoDTO basicUserInfo, String firstName, String lastName, String uBN) {
		super();
		this.keyUserInfoDTO = basicUserInfo;
		this.firstName = firstName;
		this.lastName = lastName;
		UBN = uBN;
	}

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
	 * @return Basic information about the user.
	 */
	public KeyUserInfoDTO getKeyUserInfoDTO() {
		return keyUserInfoDTO;
	}

	/**
	 * Sets basic information about the user.
	 * 
	 * @param keyUserInfoDTO Information containing token and person's type.
	 */
	public void setKeyUserInfoDTO(KeyUserInfoDTO keyUserInfoDTO) {
		this.keyUserInfoDTO = keyUserInfoDTO;
	}

	/**
	 * Maps names of the attributes with their value.
	 * 
	 * @return Filled map.
	 */
	public Map<String, String> mapAttributes() {
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("firstName", firstName);
		attributes.put("lastName", lastName);
		attributes.put("UBN", UBN);
		attributes.put("token", keyUserInfoDTO.getToken());
		attributes.put("isBlocked", String.valueOf(keyUserInfoDTO.getIsBlocked()));
		attributes.put("personType", String.valueOf(keyUserInfoDTO.getPersonType()));
		return attributes;
	}
}
