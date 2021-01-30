package pro.artse.user.errorhandling;

import java.time.LocalDate;

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
	public static boolean isNullOrEmpty(String item) {
		return item == null || item.equals("");
	}

	/**
	 * Checks if strings are null or empty.
	 * 
	 * @param item Strings to check.
	 * @return True if one of items is null or empty, false otherwise.
	 */
	public static boolean areNullOrEmpty(String... items) {
		for (String item : items)
			if (isNullOrEmpty(item))
				return true;

		return false;
	}

	public static boolean areValidDates(LocalDate date1, LocalDate date2) {
		return !((date1 == null) || (date2 == null) || (date1.compareTo(date2) > 0));
	}
}
