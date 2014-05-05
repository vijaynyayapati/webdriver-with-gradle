package functional.providers;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IEWebDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() throws IOException {
		PropertyManager props = new PropertyManager();
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(
				InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				true);
		System.setProperty("webdriver.ie.driver", pathToIEDriver(props));
		return new InternetExplorerDriver(caps);
	}

	private String pathToIEDriver(PropertyManager props) throws IOException {
		return props.getAbsolutePathForProperty("ie.driverpath");
	}
}
