package pro.artse.user.errorhandling;

/**
 * Custom validation for some objects.
 * 
 * @author Marija
 *
 */
public final class Validator {

	/**
	 * Checks if string is null or empty.
	 * 
	 * @param item String to check.
	 * @return True if it is null or empty, false otherwise.
	 */
	public static boolean IsNullOrEmpty(String item) {
		return item == null || item.equals("");
	}

	/**
	 * Checks if strings are null or empty.
	 * 
	 * @param item Strings to check.
	 * @return True if one of items is null or empty, false otherwise.
	 */
	public static boolean AreNullOrEmpty(String... items) {
		for (String item : items)
			if (IsNullOrEmpty(item))
				return true;

		return false;
	}
}
