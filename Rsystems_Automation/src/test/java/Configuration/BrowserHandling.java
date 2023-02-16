package Configuration;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.firefox.GeckoDriverService;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserHandling{


	
	public WebDriver webdriver;
	public Map<String, String> TestData = BaseTest.getTestData();

//	public WebDriver getDriver()
//	{
//		return wrapDriver(webdriver);
//	}
//	private WebDriver wrapDriver(WebDriver webdriver2) {
//		if(webdriver instanceof WrapsDriver)
//			return ((WrapsDriver) webdriver).getWrappedDriver();
//		return webdriver;
//	}
	public WebDriver ChromeDriver() {
		WebDriverManager.chromedriver().setup();
		webdriver = new ChromeDriver();
		return webdriver;
	}
	public WebDriver EdgeDriver() {
		WebDriverManager.edgedriver().setup();
		webdriver = new EdgeDriver();
		return webdriver;
	}

}

