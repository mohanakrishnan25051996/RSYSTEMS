package RSystemsPortal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Configuration.BaseTest;

public class WFH_Request extends BaseTest {
	public WebDriver webdriver;
	private static final int ElementWait = 30;
	public WebDriverWait wait;
	public Map<String, String> TestData;
	private static final By WFH_Requset = By.xpath("(//div[contains(.,'Work From Home Request')])[last()]");
	private static final By WFHType = By.xpath("//select[contains(@id,'WFHType')]");
	private static final By StartDate= By.xpath("//input[contains(@id,'StartDate')]");
	private static final By EndDate= By.xpath("//input[contains(@id,'EndDate')]");
	private static final By TabClick = By.xpath("(//td[contains(.,'End Date:')])[last()]");
	private static final By BtnSend = By
			.xpath("//a[contains(@onclick,'return SendRequest()')]/span[contains(.,'Send')]");
	private static final By ApprovalAuthority = By.xpath("//th[contains(.,'Approval Authority')]");
	private static final By CreateNewWFH_Req = By.xpath("//a[contains(.,'Create New WFH Request')]");
	private static final By StartCalender = By
			.xpath("(//input[contains(@id,'StartDate')]/following::input[contains(@type,'image')])[1]");
	private static final By StartPeriod = By.xpath("//div[contains(@id,'popupDiv')]/descendant::div[1]");
	private static final By StartNextButton = By
			.xpath("(//div[contains(@id,'container')]/descendant::div[contains(@id,'nextArrow')])[1]");
	private static final By EndCalender = By
			.xpath("(//input[contains(@id,'StartDate')]/following::input[contains(@type,'image')])[last()]");
	private static final By EndPeriod = By.xpath("(//div[contains(@id,'popupDiv')]/descendant::div[1])[last()]");
	private static final By EndNextButton = By
			.xpath("(//div[contains(@id,'container')]/descendant::div[contains(@id,'nextArrow')])[last()]");

	public WFH_Request(WebDriver webdriver, Map<String, String> TestData) {
		this.webdriver = webdriver;
		this.TestData = TestData;
		wait = new WebDriverWait(webdriver, 50, 50);
		PageFactory.initElements(webdriver, this);
	}

	public void Validate_WFH_RequestPage() {
		if (ElementVisibility(WFH_Requset))
			Print_Console("WFH Request Page is navigated or redirected");
		else
			Print_Console("Unable to redirect WFH Request Page");
	}

	public void Update_WFH_Request_Details() throws Exception {
		String startdate=TestData.get("WFH_StartDate");
		String replace= startdate.replace("/","-");
		String endDate=TestData.get("WFH_EndDate");
		String replace2 = endDate.replace("/", "-");
		SelectValue(WFHType, TestData.get("WFH_Type"));
		executeClick(StartCalender);
//		String PeriodStart = TestData.get("WFH_StartDate");
//		Date sd = new SimpleDateFormat("dd/MM/yyyy").parse(PeriodStart);
//		DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
//		String strDate = dateFormat.format(sd);
//		String[] sconv = strDate.split("\\s");
//		StartDate(sconv[0], sconv[1], sconv[2]);
		executeClick(EndCalender);
//		String PeriodEnd = TestData.get("WFH_EndDate");
//		Date ed = new SimpleDateFormat("dd/MM/yyyy").parse(PeriodEnd);
//		DateFormat end = new SimpleDateFormat("dd MMMM yyyy");
//		String EndDate = end.format(ed);
//		String[] endconv = EndDate.split("\\s");
//		EndDate(endconv[0], endconv[1], endconv[2]);
		executeClick(By.xpath("(//div[contains(@class,'calendar_day')])[1]"));
		SendKeys(StartDate, replace);
		SendKeys(EndDate, replace2);
		executeClick(TabClick);
		SelectValue( By.xpath("//td[contains(.,'"+startdate+"')]/following-sibling::td/select[contains(@id,'startHr')][1]"),TestData.get("StartHr"));
		SelectValue( By.xpath("//td[contains(.,'"+startdate+"')]/following-sibling::td/select[contains(@id,'endHr')]"), TestData.get("EndHr"));
		SelectValue( By.xpath("//td[contains(.,'"+endDate+"')]/following-sibling::td/select[contains(@id,'startHr')][1]"), TestData.get("StartHr"));		
		SelectValue( By.xpath("//td[contains(.,'"+endDate+"')]/following-sibling::td/select[contains(@id,'endHr')]"), TestData.get("EndHr"));

		executeClick(BtnSend);
		waitFor().until(ExpectedConditions.alertIsPresent());
		webdriver.switchTo().alert().accept();
	}

	public void Validate_WFH_Request_Created() {
		if (ElementVisibility(ApprovalAuthority)) {
			Print_Console("WFH Request Created");
			executeClick(CreateNewWFH_Req);
		} else
			Print_Console("WFH Request not created");
	}

	public void StartDate(String DateVal, String MonVal, String YearVal) {
		boolean mflag = false, yflag = false;
		if (DateVal.startsWith("0"))
			DateVal = DateVal.replaceAll("0", "");
		String PeriodText = GetText(StartPeriod);
		String YearText = PeriodText.replaceAll("[^\\d]", "");
		if (!(YearVal.equals(YearText))) {
			do {
				executeClick(StartNextButton);
				YearText = GetText(StartPeriod).replaceAll("[^0-9]", "").trim();
				if (!YearVal.equals(YearText))
					yflag = true;
				else
					yflag = false;
			} while (yflag);
		}
		String Monthtext = GetText(StartPeriod).replaceAll("[^A-Za-z]", "").trim();
		if (!(MonVal.equalsIgnoreCase(Monthtext))) {
			do {
				executeClick(StartNextButton);
				Monthtext = GetText(StartPeriod).replaceAll("[^A-Za-z]", "").trim();
				if (!MonVal.equals(Monthtext))
					mflag = true;
				else
					mflag = false;
			} while (mflag);
		}
		String DateSelect = "((//td[contains(@class,'calendar')])[1]/following::div[contains(text(),'" + DateVal
				+ "')])[1]";
		Highlight(By.xpath(DateSelect));
		executeClick(By.xpath(DateSelect));
	}

	public void EndDate(String DatVal, String MonthVal, String YearValue) {
		boolean mflag = false, yflag = false;
		if (DatVal.startsWith("0"))
			DatVal = DatVal.replaceAll("0", "");
		String PeriodText = GetText(EndPeriod);
		String YearText = PeriodText.replaceAll("[^\\d]", "");
		if (!(YearValue.equals(YearText))) {
			do {
				executeClick(EndNextButton);
				YearText = GetText(EndPeriod).replaceAll("[^0-9]", "").trim();
				if (!YearValue.equals(YearText))
					yflag = true;
				else
					yflag = false;
			} while (yflag);
		}
		String Monthtext = GetText(EndPeriod).replaceAll("[^A-Za-z]", "").trim();
		if (!(MonthVal.equalsIgnoreCase(Monthtext))) {
			do {
				executeClick(EndNextButton);
				Monthtext = GetText(EndPeriod).replaceAll("[^A-Za-z]", "").trim();
				if (!MonthVal.equals(Monthtext))
					mflag = true;
				else
					mflag = false;
			} while (mflag);
		}

		String EndSelect = "//td[contains(@class,'')]/following::div[contains(@id,'calEndDate_day') and contains(text(),'"
				+ DatVal + "')]";
		Highlight(By.xpath(EndSelect));
		executeClick(By.xpath(EndSelect));

	}

}
