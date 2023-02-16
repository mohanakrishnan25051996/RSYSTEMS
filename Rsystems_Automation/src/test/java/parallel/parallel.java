package parallel;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//@RunWith(Cucumber.class)
@CucumberOptions(
		features= {"./src/test/resources/parallel"},
		glue= {"parallel"},
		plugin= {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread/",
				"summary",
				"pretty",
				"json:test-output/cucumber-reports/Cucumber.json",
				"rerun:target/rerun.txt"},
		//tags="@RSystems",
		dryRun=false,
		strict=true,
		monochrome=true
		)
public class parallel extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel=true)
	public Object[][] scenarios(){
		return super.scenarios();
	}

}
