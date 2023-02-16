package parallel;

import Configuration.BaseTest;
import RSystemsPortal.LoginPage;
import RedBus_Pages.Redbus_HomePage;
import RedBus_Pages.Redbus_ResultantPage;
import io.cucumber.java.en.*;

public class Redbus_StepDef extends BaseTest {
	private String step;
	Redbus_HomePage rh=new Redbus_HomePage (webdriver,TestData);
	Redbus_ResultantPage rr=new Redbus_ResultantPage(webdriver,TestData);
	@Given("Launch Redbus Application")
	public void launch_Redbus_Application() {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		LoginPage lp=new LoginPage(webdriver,TestData);
		lp.Launch_Redbus_Application();
	}

	@When("Select Currency Type")
	public void select_Currency_Type() {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		rh.RedBus_HomePageValidation();
		rh.Choose_CuurencyType();
	}

	@Then("Search Bus")
	public void search_Bus() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		rh.SearchBus();
	}
	
	@Then("Validate Departure Time Sorted in DescendingOrder")
	public void Departure_sorting() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		rr.Sort_Departure();
	}
	
	@Then("View Seats")
	public void view_Seats() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		rr.ViewSeat();
	}
	

}
