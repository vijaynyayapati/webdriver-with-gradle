package functional.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;

import functional.providers.OS;
import functional.providers.WebDriverManager;
import functional.config.Configuration;
import functional.pages.DanMurphysHomePage;
import functional.providers.TakeScreenshotOnFailRule;

public abstract class BaseTestSetup extends WebDriverManager {

	WebDriver driver;
	private static final String pathToScreenshotDirForWindows = "\\test_reports\\functional\\screenshots";
	private static final String pathToScreenshotDirForLinux = "/test_reports/functional/screenshots";
	
	String screenshotsDirectory = System.getProperty("user.dir")
			+ getScreenShotDirectory().get(OS.getOsName());

	@Rule
	public TakeScreenshotOnFailRule fail = new TakeScreenshotOnFailRule(
			getWebDriver(), screenshotsDirectory);

	@Before
	public void setup() {
		driver = (WebDriver) WebDriverManager.getWebDriver();
	}
	
	public Map<String, String> getScreenShotDirectory(){
		Map<String, String> pathToScreenshotDir = new HashMap<String, String>();
		pathToScreenshotDir.put("Windows XP", pathToScreenshotDirForWindows);
		pathToScreenshotDir.put("Linux", pathToScreenshotDirForLinux);
		return pathToScreenshotDir;
	}

	public DanMurphysHomePage launchDanMurphysOnline() {
		driver.get(Configuration.DANMURPHYS_URL);
		return new DanMurphysHomePage(driver);
	}

	@AfterClass
	public static void tearDown() throws IOException {
		closeWebDriver();
	}
}
