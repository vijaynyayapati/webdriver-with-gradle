package functional.providers;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;

public class TakeScreenshotOnFailRule extends TestWatcher {
	protected String screenshotDirectory;
	protected WebDriver _driver;

	public TakeScreenshotOnFailRule(WebDriver driver, String folder){
		screenshotDirectory = folder;
		_driver = driver;
	}

	@Override
	protected void failed(Throwable e, Description description) {
		super.failed(e, description);
		try {
			silentlySaveScreenshotWith(description);
		} catch (Exception ex) {
			System.err.println("Can't save screenshot to "
					+ screenshotDirectory + ", " + ex);
		}
	}

	private void silentlySaveScreenshotWith(Description description) {
		try {
			FileUtils.copyFile(grabScreenshot(), new File(screenshotDirectory
					+ getTestName(description)));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private File grabScreenshot() {
		File screenshot = ((TakesScreenshot) _driver)
				.getScreenshotAs(OutputType.FILE);
		return screenshot;
	}

	private String getTestName(Description description) {
		String name = description.getMethodName();
		name = "\\" + name + ".png";
		return name;
	}

	@Override
	protected void finished(Description description) {
		_driver.manage().deleteAllCookies();
	}
}