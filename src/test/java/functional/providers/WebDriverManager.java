package functional.providers;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;

import functional.providers.Browser;

public class WebDriverManager {

	private static List<Browser> allBrowsers = Collections
			.synchronizedList(new LinkedList<Browser>());
	public static final String FIREFOX_DRIVER = "Firefox";
	public static final String CHROME_DRIVER = "Chrome";
	private static final String DEFAULT_DRIVER = CHROME_DRIVER;

	private static ThreadLocal<Browser> webDriver = new ThreadLocal<Browser>() {
		@Override
		protected Browser initialValue() {
			Browser browsers = getInstance();
			allBrowsers.add(browsers);
			return browsers;
		}
	};

	@SuppressWarnings("serial")
	private static final Map<String, WebDriverFactory> TYPE_TO_FACTORY_MAP = new HashMap<String, WebDriverFactory>() {
		{
			put(FIREFOX_DRIVER, new FirefoxWebDriverFactory());
			put(CHROME_DRIVER, new ChromeWebDriverFactory());
		}
	};

	private static Browser create() throws Exception {
		String driverType = getDriverType();
		System.out.println("Initialising " + driverType);
		if (TYPE_TO_FACTORY_MAP.containsKey(driverType)) {
			WebDriverFactory factory = TYPE_TO_FACTORY_MAP.get(driverType);
			return new Browser(factory.create());
		}
		return new Browser(new ReflectionWebDriverFactory(driverType).create());
	}

	private static Browser getInstance() {
		try {
			return create();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Browser browser() {
		return webDriver.get();
	}

	public static WebDriver getWebDriver() {
		return browser().getWebDriver();
	}

	public static void closeWebDriver() {
		browser().getWebDriver().quit();
	}

	private static String getDriverType() {
		return System.getProperties().getProperty("browser", DEFAULT_DRIVER);
	}
}
