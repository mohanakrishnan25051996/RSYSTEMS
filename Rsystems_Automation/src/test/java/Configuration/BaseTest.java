package Configuration;

import java.awt.AWTException;
import java.awt.Robot;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Scenario;

public abstract class BaseTest {
	public static WebDriver webdriver = null;
	public static Scenario scenario;
	public static Map<String, String> TestData;
	public static Map<String, String>OutputResult=new HashMap<String, String>();
	public static String ScenarioName= null, RemoteExecution=null, BrowserType=null, step=null;
	public static WebDriverWait wait;
	private static List<WebElement> until;
	public BaseTest setup(Scenario scenario, String ScenarioName,Map<String, String> TestData) {
		setScenario(scenario);
		if(ScenarioName.isEmpty()||ScenarioName==null) {
			setScenarioName(this.getClass().getSimpleName());
		}
		else {
			setScenarioName(ScenarioName);
		}
		setTestData(TestData);
		return this;
	}
	public void setScenario(Scenario scenario)
	{
		this.scenario  =scenario;
	}
	public static Scenario getScenario() {
		return scenario;
	}
	public void setTestData(Map<String, String> TestData)
	{
		this.TestData  =TestData;
	}
	public static Map<String, String>  getTestData() {
		return TestData;
	}
	public void setScenarioName(String ScenarioName)
	{
		this.ScenarioName  =ScenarioName;
	}
	public static String getScenarioName() {
		return ScenarioName;
	}
	public WebDriver getDriver() {
		BrowserHandling bh=new BrowserHandling();
		if(TestData.get("BrowserType").equalsIgnoreCase("chrome"))			
			this.webdriver=bh.ChromeDriver();
		else if(TestData.get("BrowserType").equalsIgnoreCase("edge"))
			this.webdriver=bh.EdgeDriver();
			return webdriver;
	}
	public static String getCurrentStep() {
		return step;
	}
	public void setCurrentStep(String Step) {
		this.step= step;
	}

	public static void ClearCookies() {
		webdriver.manage().deleteAllCookies();
	}
	public static WebDriverWait waitFor() {
		wait = new WebDriverWait(webdriver,30);
		return wait;
	}
	public static void Highlight(By element) {
		WebElement wb=webdriver.findElement(element);
		((JavascriptExecutor)webdriver).executeScript("arguments[0].scrollIntoView();", wb);
		((JavascriptExecutor)webdriver).executeScript("arguments[0].setAttribute('style','background: yellow; border:2px solid red;');",wb);
	}
	public void executeClick(By element){
		waitFor().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		WebElement wb=webdriver.findElement(element);
		try {			
			wb.click();
		}catch(Exception e)
		{
			JavascriptExecutor js=(JavascriptExecutor)webdriver;
			js.executeScript("arguments[0].click();",wb);
		}
	}
	public void SelectValue(By element, String Value) throws Exception
	{
		waitFor().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		WebElement wb=webdriver.findElement(element);
		try {
			Select op=new Select(wb);
			op.selectByVisibleText(Value);
		}catch(Exception e)
		{
			throw new Exception("No Value to Select"+e.getMessage());
		}
	}
	public static void AddScreenshot_runtime() {
		TakesScreenshot ts=(TakesScreenshot)webdriver;
		byte[] screenshot=ts.getScreenshotAs(OutputType.BYTES);
		//scenario.embed(screenshot, "image/png",getCurrentStep());
	}
	public static void SendKeys(By element,String Value) throws Exception{
		waitFor().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		WebElement wb=webdriver.findElement(element);
		try {
			wb.clear();
			wb.sendKeys(Value);
		}catch(Exception e)
		{
			JavascriptExecutor js=(JavascriptExecutor)webdriver;
			js.executeScript("arguments[0].setAttribute('value','"+Value+"')",wb);
		}
	}
	public static void SendKeys_WithoutClearing(By element,Keys Value) throws Exception{
		waitFor().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		WebElement wb=webdriver.findElement(element);
		try {
			wb.sendKeys(Value);
		}catch(Exception e)
		{
			JavascriptExecutor js=(JavascriptExecutor)webdriver;
			js.executeScript("arguments[0].setAttribute('value','"+Value+"')",wb);
		}
	}
	public static boolean ElementVisibility(By element) {
		try {
			waitFor().until(ExpectedConditions.visibilityOfElementLocated(element));
			Highlight(element);
			return true;
		}catch(Exception e)
		{
			if(webdriver.findElements(element).size()>0)
			{
				Highlight(element);
				return true;
			}
			else
				return false;
		}
	}
	public static void Print_Console(String Value) {
		scenario.log(Value);
	
		
		System.out.println(Value);
	}
	public static String GetText(By element)
	{
		waitFor().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
		return webdriver.findElement(element).getText();
	}
	public static void ScrollDown() {
		JavascriptExecutor js=(JavascriptExecutor)webdriver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	public static void ScrollUp() {
		JavascriptExecutor js=(JavascriptExecutor)webdriver;
		js.executeScript("window.scrollBy(0,-350", "");
	}
	public static boolean TimeSortValidation_Descendending(ArrayList<Date> arr) {
		List<Date> copyof=new ArrayList<>(arr);
		Collections.sort(copyof, Collections.reverseOrder());
		if(arr.equals(copyof))
			return true;
		else
			return false;
	}
	
	public static void robotKeyPress(int keycode) throws AWTException{
		Robot robot = new Robot();
		robot.keyPress(keycode);
}
	public static void robotKeyRelease(int keycode) throws AWTException{
		Robot robot = new Robot();
		robot.keyPress(keycode);
}

	public void fetchFirst5Products(By element){
	int count = 1;
	List<WebElement> list = webdriver.findElements(element);
	if(list.size()!=0) {
		for(int i=0;i<list.size()-1;i++) {
			WebElement listOfProduct = list.get(i);
			String amount = listOfProduct.getText();
			int amont = Integer.parseInt(amount.replaceAll("[^0-9]",""));
			if(amont<=30000) {
				String title = webdriver.findElement(By.xpath("")).getText();
				System.out.println("Product: "+ count+ "title is "+ title);
				count++;
			}
			if(count >=6) 
			break;
		}
	}
	
}
 public void fetchLowestAndHighestProduct(By element) {
	 HashMap<Integer,String> map = new HashMap<>();
	 List<WebElement> list = webdriver.findElements(element);
	 if(list.size()!=0) {
		 for(int i=0; i<list.size();i++) {
			 	String amount = webdriver.findElement(By.xpath("")).getText();
			 	String title = webdriver.findElement(By.xpath("")).getText();
			 	amount= amount.replaceAll("[^0-9]","");
			 	int price = Integer.parseInt(amount);
			 	map.put(price, title);
		}
		 Set<Integer> keys = map.keySet();
		 ArrayList<Integer> listOfSortProduct = new  ArrayList<Integer>(keys);
		 Collections.sort(listOfSortProduct);
		 
		 Integer getLowest = listOfSortProduct.get(0);
		 Integer getHighest = listOfSortProduct.get(listOfSortProduct.size()-1);
		 
		 System.out.println(getLowest);
		 System.out.println(getHighest);
	 }
 }	
}