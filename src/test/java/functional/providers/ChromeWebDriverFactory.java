package functional.providers;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeWebDriverFactory implements WebDriverFactory {

	public WebDriver create() throws Exception {
		PropertyManager props = new PropertyManager();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver(props));
		HashMap<String, Object> chromeOptions = new HashMap<String, Object>();
		putChromeBinaryInChromeOptions(chromeOptions, props);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return new ChromeDriver(capabilities);
	}

	private static void putChromeBinaryInChromeOptions(
			HashMap<String, Object> chromeOptions, PropertyManager props)
			throws IOException {
		chromeOptions
				.put("binary", props.getPropertyValue("chrome.binarypath"));
	}

	private static String pathToChromeDriver(PropertyManager props)
			throws IOException {
		return props.getAbsolutePathForProperty("chrome.driverpath");
	}

}
