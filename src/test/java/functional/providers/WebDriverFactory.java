package functional.providers;

import org.openqa.selenium.WebDriver;

interface WebDriverFactory {
	WebDriver create() throws Exception;

}
