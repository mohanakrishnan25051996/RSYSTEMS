package RSystemsPortal;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuration.BaseTest;

public class Cancellation_WFH_Request extends BaseTest{
	public WebDriver webdriver;
	public Map<String,String> TestData;
	private static final By WFH_CancellationTAB= By.xpath("//a[contains(@id,'WFHCancellationTab')]");
	private static final By Pending_CancelledRequest =By.xpath("(//tr[contains(.,'Pending/Cancelled Requests')])[last()]");
	private static final By WFH_CancelReason= By.xpath("//textarea[contains(@name,'Reason')]");
	private static final By WFH_SendReason= By.xpath("//a[contains(@id,'SendReason')]/span[contains(.,'Send')]");


	public Cancellation_WFH_Request(WebDriver webdriver,Map<String,String> TestData) {
		this.webdriver=webdriver;
		this.TestData=TestData;
		PageFactory.initElements(webdriver, this);
	}

	public void RedirectToWFHCancel() {
		executeClick(WFH_CancellationTAB);
	}
	public void Cancel_WFH_Request() throws Exception {		
		if(ElementVisibility(Pending_CancelledRequest))
		{
			String StartDate=TestData.get("WFH_StartDate").replace("-", "/");
			String EndDate=TestData.get("WFH_EndDate").replace("-", "/");
			String Tablerow="//tr[contains(.,'"+StartDate+"') and contains(.,'"+EndDate+"') and contains(@class,'white')]//td/a";
			executeClick(By.xpath(Tablerow));
			SendKeys(WFH_CancelReason, TestData.get("Cancel_Reason"));
			executeClick(WFH_SendReason);
			waitFor().until(ExpectedConditions.alertIsPresent());
			webdriver.switchTo().alert().accept();
			String CancelledRequest="//tr[contains(.,'"+StartDate+"') and contains(.,'"+EndDate+"') and contains(@class,'white')]//td[last()]";
			String UpdatedReason=GetText(By.xpath(CancelledRequest)).toUpperCase();
			Highlight(By.xpath(CancelledRequest));
			if(UpdatedReason.equals("CANCELLED"))
				System.out.println("WFH REQUEST Cancelled Successfully");
//				Print_Console("WFH REQUEST Cancelled Successfully");
			else
				System.out.println("Unable to cancel WFH Request");
//				Print_Console("Unable to cancel WFH Request");
		}
	}
}
