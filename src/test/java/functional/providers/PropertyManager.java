package functional.providers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

	private Properties properties = new Properties();
	private PropertyManager props;

	public Properties loadProperties() throws IOException {
		FileInputStream fis = null;
		if (OS.isOSX()) {
			try {
				fis = new FileInputStream(getWorkingDirectory()
						+ "/osx.properties");
				properties.load(fis);
			} catch (FileNotFoundException ex) {
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		} else {
			fis = new FileInputStream(getWorkingDirectory()
					+ "/linux.properties");
			try {
				properties.load(fis);
			} catch (FileNotFoundException ex) {
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		}
		return properties;
	}

	public String getAbsolutePathForProperty(String key) throws IOException {
		return getWorkingDirectory() + getPropertyValue(key);
	}

	public String getPropertyValue(String key) throws IOException {
		props = new PropertyManager();
		return props.loadProperties().getProperty(key);
	}

	private static String getWorkingDirectory() {
		return System.getProperty("user.dir");
	}
}
