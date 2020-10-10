package pro.artse.dal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Marija
 * Manipulates with project's configuration.
 */
public final class ConfigurationUtil {

	/** Reads value that corresponds provided key, from configuration file.
	 * @param key Key that identifies value.
	 * @return Value if found or null.
	 */
	public static String get(String key) {
		Properties prop = new Properties();
		String fileName = "config.properties";
		InputStream is = null;
		try (InputStream inputStream = new FileInputStream(fileName)) {
			prop.load(is);
			return prop.getProperty(key);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
