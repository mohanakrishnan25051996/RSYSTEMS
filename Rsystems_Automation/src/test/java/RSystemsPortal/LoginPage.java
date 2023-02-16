package RSystemsPortal;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Configuration.BaseTest;


public class LoginPage extends BaseTest{
	public WebDriver webdriver;
	private static final int ElementWait=30;
	public WebDriverWait wait;
	public Map<String,String> TestData;
	private static final By Login=By.xpath("");
	public LoginPage(WebDriver webdriver,Map<String,String> TestData) {
		this.webdriver=webdriver;
		this.TestData=TestData;
//		wait=new WebDriverWait(webdriver, Duration.ofSeconds(50));
		wait=new WebDriverWait(webdriver, 50,50);
		PageFactory.initElements(webdriver, this);
	}
	public void Launch_Rsystem_Application() {
		String Portal =TestData.get("Portal");
		String Username=System.getenv("RSystems_Username");
		String Password=System.getenv("RSystems_Password");
		webdriver.manage().window().maximize();
		Portal="https://"+Username+":"+Password+"@"+Portal;
		String requrl="window.open(\""+Portal+"\",\"_self\")";
		ClearCookies();
		((JavascriptExecutor)webdriver).executeScript(requrl);
		webdriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}
	public void Launch_Redbus_Application() {
		String URL=TestData.get("URL");
		webdriver.manage().window().maximize();
		ClearCookies();
		webdriver.get(URL);
		webdriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	}
	
}
