
import config.BaseCucumber;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"@target/rerun.txt"},
        monochrome = true,
        glue = {"stepdefinition", "com.apis.globedr.stepdefinition", "config"},
        plugin = {
                //"listeners.Hooks::custom-formatter-output.txt", // this Hooks use for info.cukes.cucumber
                //"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure report
                "listeners.LoggerPlugin", // this Listener use for io.cucumber
                "rerun:target/rerun.txt",
                "json:target/result.json"
        }
)
public class FailedScenarios extends BaseCucumber {

}
