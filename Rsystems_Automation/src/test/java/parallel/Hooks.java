package parallel;

import java.util.HashMap;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Configuration.BaseTest;
import Configuration.DataHandler;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class Hooks extends BaseTest{

	public static Scenario scenario;
	public String ScenarioName="";
	public String FeatureName="",SheetName="";
	public WebDriver webdriver; 
	public DataHandler datahandler=null;
	public HashMap<String,String> TestData=new HashMap<String,String>();

	@Before
	public void before(Scenario scenario) throws Throwable{
		Hooks.scenario =scenario;
		ScenarioName = scenario.getName();
		String[] tab = scenario.getId().split("/");
		int rawFeatureNameLength = tab.length;
		String featureName = tab[rawFeatureNameLength - 1].split(":")[0];
		FeatureName = featureName.replace(".feature", "");
		scenario.log("Feature :"+FeatureName);
		scenario.log("Scenario Name :"+ScenarioName);
		System.out.println("Feature :" + FeatureName);
		System.out.println("Scenario Name :" +ScenarioName);
		SheetName=FeatureName;
		datahandler= new DataHandler(System.getProperty("user.dir")+"/src/test/java/DataSheet/RSystemdatasheet.xls",FeatureName,ScenarioName);
		TestData=DataHandler.ReadExcel();
		super.setup(scenario, ScenarioName, TestData);
		webdriver=getDriver();
	}

	@AfterStep()
	public void afterSite() { 
		TakesScreenshot ts = (TakesScreenshot) webdriver;
		byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot,"image/png", getCurrentStep());
	}

	@After()
	public void after() {
		System.out.println("##################################");
		if(scenario.isFailed()) {
			System.out.println("Scenario :"+ getScenarioName()+" Status:Failed");
			TakesScreenshot ts = (TakesScreenshot) webdriver;
			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot,"image/png", getCurrentStep());
		}
		else {
			System.out.println("Scenario :"+ getScenarioName()+" Status:Passed");

		}
		System.out.println("##################################");
		for(String handle : webdriver.getWindowHandles()) {
			webdriver.switchTo().window(handle).close();
		}
	}
}
