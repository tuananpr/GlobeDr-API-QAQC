package stepdefinition.healthStep;

import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.*;
import com.apis.globedr.model.step.health.BloodGlucoseMS;


import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.*;

public class HealthBloodGlucoseStep extends Data {

    HealthBus healthBus = new HealthBus();


    @When("^I add Blood Glucose with info$")
    public void addBloodGlucoseWithInfo(List<BloodGlucoseMS> list) {
        list.forEach(info ->{
            info.setHealthDataSig(userSig);
            healthBus.addBloodGlucose(info);
        });
    }

    @And("^I load Blood Glucose$")
    public void loadBloodGlucose(BloodGlucoseMS info) {
        info.setHealthDataSig(userSig);
        healthBus.loadBloodGlucose(info);
    }

    @And("^I load Blood Glucose Chart$")
    public void loadBloodGlucoseChart(BloodGlucoseMS info) {
        info.setHealthDataSig(userSig);
        healthBus.loadBloodGlucoseChart(info);
    }


}