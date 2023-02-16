package RSystemsPortal;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import Configuration.BaseTest;

public class HomePage extends BaseTest {
	public WebDriver webdriver;
	private static final int ElementWait = 30;
	public WebDriverWait wait;
	public Map<String, String> TestData;
	private static final By HomePage = By.xpath("(//td[contains(.,'Home')])[last()]");
	private static final By Leave = By.xpath("//span[contains(.,'Leave & Attendance')]");
	private static final By WFHRequest = By.xpath("(//td[contains(.,'Work From Home Request')])[last()]");

	private static final By Rsystem_User = By.xpath("//span[contains(@id,'lblUserName')]");

	public HomePage(WebDriver webdriver, Map<String, String> TestData) {
		this.webdriver = webdriver;
		this.TestData = TestData;
//		wait=new WebDriverWait(webdriver, Duration.ofSeconds(50));
		wait=new WebDriverWait(webdriver, 50,50);
		PageFactory.initElements(webdriver, this);
	}

	public void HomePageValidation() {
		ElementVisibility(HomePage);
		if (ElementVisibility(Rsystem_User))
			System.out.println("Successfully validated the user name");
//			Print_Console("Successfully validated the user name");
		else
			System.out.println("Unable to find user name");
//			Print_Console("Unable to find user name");
	}

	public void Navigate_WFH_RequestPage() {
		if (ElementVisibility(Leave)) {
			executeClick(Leave);
			if (ElementVisibility(WFHRequest)) {
				executeClick(WFHRequest);
			} else
				System.out.println("Unable to find WFH Request under Leave & Attendance");
//				Print_Console("Unable to find WFH Request under Leave & Attendance");
		} else
			System.out.println("Unable to find Leave & Attendance");
//			Print_Console("Unable to find Leave & Attendance");
	}

}
