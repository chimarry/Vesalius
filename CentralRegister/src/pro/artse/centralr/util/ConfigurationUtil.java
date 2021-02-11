package pro.artse.centralr.util;

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
		ResourceBundle bundle = PropertyResourceBundle.getBundle("pro.artse.centralr.util.config");
		return bundle.getString(key);
	}

	public static int getMeteres() {
		return Integer.parseInt(get("k"));
	}

	public static int getTimeInterval() {
		return Integer.parseInt(get("p"));
	}
}
