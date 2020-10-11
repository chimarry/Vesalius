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
public class User {

	/**
	 * Information about the user that can be displayed to the public.
	 */
	private BasicUserInfo basicUserInfo;

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
	public User() {

	}

	/**
	 * Creates user object.
	 * @param basicUserInfo Public information about the user.
	 * @param firstName First name of the user.
	 * @param lastName Last name of the user.
	 * @param uBN Unique birth number of the user.
	 */
	public User(BasicUserInfo basicUserInfo, String firstName, String lastName, String uBN) {
		super();
		this.basicUserInfo = basicUserInfo;
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
	public BasicUserInfo getBasicUserInfo() {
		return basicUserInfo;
	}

	/**
	 * Sets basic information about the user.
	 * 
	 * @param basicUserInfo Information containing token and person's type.
	 */
	public void setBasicUserInfo(BasicUserInfo basicUserInfo) {
		this.basicUserInfo = basicUserInfo;
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
		attributes.put("isDeactivated", String.valueOf(basicUserInfo.isDeactivatedInt()));
		attributes.put("token", basicUserInfo.getToken());
		attributes.put("personType", String.valueOf(basicUserInfo.getPersonType()));
		return attributes;
	}
}
