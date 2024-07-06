package ManUTDRegression.framework.generic;

import java.io.IOException;
import java.util.Properties;

public class DesiredConfigLoader {
	
	private static Properties properties;

	private DesiredConfigLoader() {
	}

	public static Properties getInstance() {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(
						DesiredConfigLoader.class.getClassLoader()
						.getResourceAsStream("properties/desiredConfig.properties"));
			} catch (IOException e) {
				System.exit(0);
			}
		}

		return properties;
	}

	/**
	 * Method to get the properties value for a given key.
	 *
	 * @param key
	 * @return The String value.
	 */
	public static String getProperty(String key) {
		return DesiredConfigLoader.getInstance().getProperty(key);
	}

	/**
	 * Create the instance for the first time.
	 */
	public static void init() {
		DesiredConfigLoader.getInstance();
	}
	
	public static String getUser() {
		String user = getProperty("user");
		if(user == null || !(user.trim().length() > 0)){
			user = "System";
		}
		return user;
	}
}