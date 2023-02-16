package parallel;

import Configuration.BaseTest;
import RSystemsPortal.Cancellation_WFH_Request;
import RSystemsPortal.HomePage;
import RSystemsPortal.LoginPage;
import RSystemsPortal.WFH_Request;
import io.cucumber.java.en.*;

public class RSystems_StepDef extends BaseTest{
	
	
	LoginPage lp= new LoginPage(webdriver, TestData);
	HomePage hp=new HomePage(webdriver,TestData);
	WFH_Request WFH_Req=new WFH_Request(webdriver,TestData);
	Cancellation_WFH_Request WFH_cancel=new Cancellation_WFH_Request(webdriver,TestData);
	
	@Given("User launch RSystems application")
	public void user_launch_RSystems_application() {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		lp.Launch_Rsystem_Application();
			}

	@When("Verify user logged successfully")
	public void verify_user_logged_successfully() {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		hp.HomePageValidation();
	}

	@When("Navigate to Work From Home Request module")
	public void navigate_to_Work_From_Home_Request_module() {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		hp.Navigate_WFH_RequestPage();
	}

	@When("Create WFH request")
	public void create_WFH_request() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		WFH_Req.Validate_WFH_RequestPage();
		WFH_Req.Update_WFH_Request_Details();
		WFH_Req.Validate_WFH_Request_Created();
		
	}

	@When("Navigate to WFH Cancellation Tab")
	public void navigate_to_WFH_Cancellation_Tab() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		WFH_cancel.RedirectToWFHCancel();

	}

	@Then("Verify WFH Request created")
	public void verify_WFH_Request_created() throws Exception {
		step=new Throwable().getStackTrace()[0].getMethodName();
		setCurrentStep(step);
		WFH_cancel.Cancel_WFH_Request();


	}

}
