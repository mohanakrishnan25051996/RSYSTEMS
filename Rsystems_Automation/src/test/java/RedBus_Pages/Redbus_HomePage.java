package RedBus_Pages;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import Configuration.BaseTest;

public class Redbus_HomePage extends BaseTest{
	public WebDriver webdriver;
	private static final int ElementWait=30;
	public WebDriverWait wait;
	public Map<String,String> TestData;
	private static final By RedbusHome=By.xpath("//img[contains(@class,'redbus-logo')]");
	private static final By Currency=By.xpath("//div[contains(@id,'curr_chosen')]");
	private static final By AcceptCookies =By.xpath("//button[contains(.,'Accept All')]");
	private static final By SOURCE = By.xpath("//input[contains(@id,'src')]");	
	private static final By DESTINATION=By.xpath("//input[contains(@id,'dest')]");
	private static final By STARTDATE = By.xpath("//input[contains(@id,'onward_cal')]");	
	private static final By RETURNDATE=By.xpath("//input[contains(@id,'r-date')]");
	private static final By SearchButton = By.xpath("//button[contains(@id,'search_butn')]");
	private static final By MainPeriod=By.xpath("//div[contains(@class,'MainBlock')][1]/descendant::div[1]");
	private static final By NextPeriod = By.xpath("(//*[local-name()='svg'])[last()]");
	public Redbus_HomePage(WebDriver webdriver,Map<String,String> TestData) {
		this.webdriver=webdriver;
		this.TestData=TestData;
		wait=new WebDriverWait(webdriver, 50,50);
		PageFactory.initElements(webdriver, this);
	}

	public void RedBus_HomePageValidation() {
		if(ElementVisibility(RedbusHome))		
			Print_Console("Launched Redbus Application");
		else
			Print_Console("Unable to launch Redbus Apllication");
	}

	public void Choose_CuurencyType() {
		if(ElementVisibility(Currency))
		{
			executeClick(Currency);
			String CurrencyType=TestData.get("Currency");

			String CurrencyXpath="//li[contains(@data-currency,'"+CurrencyType+"')]";
			if(ElementVisibility(By.xpath(CurrencyXpath)))
			{
				executeClick(By.xpath(CurrencyXpath));
			}
			else
				Print_Console("Unable to find user specific Currency Type");
		}
		else
			Print_Console("Unable to find currency Field");
		if(ElementVisibility(AcceptCookies))		
			executeClick(AcceptCookies);
	}

	public void SearchBus() throws Exception {
		String src=TestData.get("Source");
		String Dst=TestData.get("Destination");
		String StartDate=TestData.get("StartDate");
		String RetDate=TestData.get("ReturnDate");
		SendKeys(SOURCE, src);
		String srcL=TestData.get("SourceLocation");
		executeClick(By.xpath("(//li[contains(.,'"+srcL+"')])[last()]"));
		SendKeys(DESTINATION, Dst);
		String srcD=TestData.get("DestinationLocation");
		executeClick(By.xpath("//li[contains(.,'"+srcD+"')]"));	
		executeClick(STARTDATE);
		String StartPeriod = TestData.get("StartDate");
		Date sd=new SimpleDateFormat("dd/MM/yyyy").parse(StartPeriod);
		DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");  
		String strDate = dateFormat.format(sd);  
		String[] sconv = strDate. split("\\s");		
		SelectData(sconv[0],sconv[1],sconv[2]);
		executeClick(RETURNDATE);
		String RetPeriod = TestData.get("ReturnDate");
		Date rd=new SimpleDateFormat("dd/MM/yyyy").parse(RetPeriod);
		String retDate = dateFormat.format(rd);  
		String[] rconv = retDate. split("\\s");		
		SelectData(rconv[0],rconv[1],rconv[2]);
		executeClick(SearchButton);
	}
	
	public void SelectData(String DateVal,String MonVal, String YearVal) {
		boolean mflag=false,yflag=false;
		if(DateVal.startsWith("0"))
			DateVal=DateVal.replaceAll("0", "");
		String PeriodText=GetText(MainPeriod);
		String YearText=PeriodText.replaceAll("[^\\d]", "");
		if(!(YearVal.equals(YearText)))
		{
			do {
				executeClick(NextPeriod);
				YearText=GetText(MainPeriod).replaceAll("[^0-9]", "").trim();
				if(!YearVal.equals(YearText))
					yflag=true;
				else
					yflag=false;
			}while(yflag);			
		}		
		String Monthtext=GetText(MainPeriod).replaceAll("[^A-Za-z]", "").trim();
		if(!(MonVal.equalsIgnoreCase(Monthtext)))
		{
			do {				
				executeClick(NextPeriod);
				Monthtext=GetText(MainPeriod).replaceAll("[^A-Za-z]", "").trim();
				if(!MonVal.equals(Monthtext))
					mflag=true;	
				else
					mflag=false;
			}while(mflag);			
		}
		String DateSelect="//div[contains(@class,'DatePicker__MainBlock')][1]/descendant::span[text()='"+DateVal+"']";
		executeClick(By.xpath(DateSelect));
	}
}
