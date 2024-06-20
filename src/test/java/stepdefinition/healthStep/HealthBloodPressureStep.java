package stepdefinition.healthStep;

import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.health.BloodPressureMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.List;

public class HealthBloodPressureStep extends Data {


    HealthBus healthBus = new HealthBus();


    @When("^I add Blood Pressure$")
    public void addBloodPressure(List<BloodPressureMS> list) {
        list.forEach(info ->{
            info.setUserSig(userSig);
            healthBus.addBloodPressure(info);
        });
    }


    @And("^I load my Blood Pressure$")
    public void loadBloodPressure(BloodPressureMS info) {
        info.setUserSig(userSig);
        healthBus.loadBloodPressure(info);
    }

    @When("^I load chart Blood Pressure$")
    public void loadBloodPressureChart(BloodPressureMS info) {
        info.setUserSig(userSig);
        healthBus.loadBloodPressureChart(info);
    }


    @When("^I load last Vitals$")
    public void loadLastVital(BloodPressureMS info) {
        info.setUserSig(userSig);
        healthBus.loadLastVitals(info);
    }


}