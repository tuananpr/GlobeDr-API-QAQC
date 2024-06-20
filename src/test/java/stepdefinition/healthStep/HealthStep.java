package stepdefinition.healthStep;

import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.*;
import com.apis.globedr.model.request.health.UpdateMeasurementUnitRQ;
import com.apis.globedr.model.step.health.*;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class HealthStep extends Data {

    HealthBus healthBus = new HealthBus();


    @Then("^I change blood type with below info$")
    public void saveBloodType(HealthMS info) {
        info.setUserSig(userSig);
        healthBus.saveBloodType(info);
    }

    @And("^I update measurement unit$")
    public void updateMeasurementUnit(UpdateMeasurementUnitRQ info) {
        info.setUserSig(userSig);
        healthBus.updateMeasurementUnit(info);
    }



    @And("^I load health status$")
    public void iLoadHealthStatus(HealthMS info) {
        info.setUserSig(userSig);
        healthBus.loadStatus(info);
    }





}