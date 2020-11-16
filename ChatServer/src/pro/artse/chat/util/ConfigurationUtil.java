package pro.artse.chat.util;

import java.net.InetSocketAddress;
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
		ResourceBundle bundle = PropertyResourceBundle.getBundle("pro.artse.chat.util.config");
		return bundle.getString(key);
	}
	
	public static InetSocketAddress getInetSocketAddress(String portKeyName, String addressKeyName) {
		String address = ConfigurationUtil.get(addressKeyName);
		int port = Integer.parseInt(ConfigurationUtil.get(portKeyName));
		return new InetSocketAddress(address, port);
	}
}
