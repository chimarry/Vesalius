package pro.artse.tokenserver.models;

import java.io.Serializable;

/**
 * Represents information needed for user to register on Token server.
 * 
 * @author Marija
 *
 */
public class Credentials implements Serializable {

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
	 * Creates object of type Credentials, with initialized fields.
	 */
	public Credentials(String firstName, String lastName, String ubn) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.UBN = ubn;
	}

	/**
	 * Returns first name of the user.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name of the user.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns UBN of the user.
	 */
	public String getUBN() {
		return UBN;
	}
}
