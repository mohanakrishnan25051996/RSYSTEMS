package Runner;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features= {"./src/test/resources/features"},
		glue= {"StepDef"},
		plugin= {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"summary",
				"pretty",
				"json:test-output/cucumber-reports/Cucumber.json",
				"rerun:target/rerun.txt"},
		tags="@RSystems",
		dryRun=false,
		strict=true,
		monochrome=true
		)
public class RunnerFile extends AbstractTestNGCucumberTests{
	
	

}