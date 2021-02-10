package pro.artse.user.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Marija Manipulates with project's configuration.
 */
public final class ConfigurationUtil {

	/**
	 * Reads value that corresponds provided key, from configuration file.
	 * 
	 * @param key Key that identifies value.
	 * @return Value if found or null.
	 */
	public static String get(String key) {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("pro.artse.user.util.config");
		return bundle.getString(key);
	}
}
