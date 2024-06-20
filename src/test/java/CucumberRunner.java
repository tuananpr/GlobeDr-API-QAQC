
import config.BaseCucumber;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/"},
        //publish = true,
        //features = {"."},
        monochrome = true,
        glue = {"stepdefinition", "com.apis.globedr.stepdefinition", "config"},
        tags = "@user-call-customer-1",
        plugin = {
                //"listeners.Hooks::custom-formatter-output.txt", // this Hooks use for info.cukes.cucumber
                //"html:target/cucumber-reports.html",
                "listeners.LoggerPlugin", // this Listener use for io.cucumber
                //"listeners.InfluxDbPlugin", // save all request to db
                "rerun:target/rerun.txt",
                //"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure report
                "json:target/result.json"

        }
)


public class CucumberRunner extends BaseCucumber {

}