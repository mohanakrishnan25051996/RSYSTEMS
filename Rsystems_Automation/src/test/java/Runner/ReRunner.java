package Runner;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features= {"@target/rerun.txt"},
		glue= {"StepDef"},
		plugin= {"summary"},
		dryRun=false,
		strict=true,
		monochrome=true
		)
public class ReRunner extends AbstractTestNGCucumberTests{
	

}