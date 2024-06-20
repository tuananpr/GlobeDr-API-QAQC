package stepdefinition;

import com.rest.core.debug.CucumberReport;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;

public class HookStep {

    @Before
    public void start(Scenario scenario) {
        CucumberReport.setScenario(scenario);
    }


    @After
    public void end(Scenario scenario) {

    }




}
