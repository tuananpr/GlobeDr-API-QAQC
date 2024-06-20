package stepdefinition;


import com.apis.globedr.apis.GuideApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Data;

import java.util.Map;

public class GuideSteps extends Data {

    GuideApi guideApi = GuideApi.getInstant();



    @When("^I load guides$")
    public void iLoadGuides(Map<String, Object> dataTable) {
        guideApi.guides(Common.getMap(dataTable));
    }

    @When("^I update screen$")
    public void iUpdateScreen(Map<String, Object> dataTable) {
        guideApi.updateScreen(Common.getMap(dataTable));
    }

    @When("^I delete screen$")
    public void iDeleteScreen(Map<String, Object> dataTable) {
        guideApi.deleteScreen(Common.getMap(dataTable));
    }

    @When("^I load screens$")
    public void iLoadScreens(Map<String, Object> dataTable) {
        guideApi.loadScreens(Common.getMap(dataTable));
    }

    @When("^I new screen$")
    public void iNewScreen(Map<String, Object> dataTable) {
        guideApi.newScreen(Common.getMap(dataTable));
    }

    @When("^I new guide$")
    public void iNewGuide(Map<String, Object> dataTable) {
        guideApi.newGuide(Common.getMap(dataTable));
    }

    @When("^I update guide$")
    public void iUpdateGuide(Map<String, Object> dataTable) {
        guideApi.updateGuide(Common.getMap(dataTable));
    }

    @And("^I delete guide$")
    public void iDeleteGuide(Map<String, Object> dataTable) {
        guideApi.deleteGuide(Common.getMap(dataTable));
    }
}
