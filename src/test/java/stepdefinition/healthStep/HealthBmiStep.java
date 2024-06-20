package stepdefinition.healthStep;


import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.health.BmiMS;

import io.cucumber.java.en.When;
import java.util.List;


public class HealthBmiStep extends Data {


    HealthBus healthBus = new HealthBus();

    @When("^I add BMI$")
    public void addBMI(List<BmiMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            healthBus.addBMI(info);
        });
    }

    @When("^I delete BMI$")
    public void deleteBMI(List<BmiMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setHealthDataSig(healthBus.getBmiSig(info));
            healthBus.deleteHealthData(info);
        });
    }

    @When("^As user, I load last BMI$")
    public void userLoadLastBMI(BmiMS info) {
        info.setUserSig(userSig);
        healthBus.loadLastBMI(info);
    }

    @When("^As manager, I load last BMI$")
    public void orgloadLastBMI(BmiMS info) {
        info.setUserSig(userSig);
        info.setOrgSig(orgSig);
        healthBus.loadLastBMI(info);
    }

    @When("^I load chart BMI$")
    public void loadBMIChart(BmiMS info) {
        info.setUserSig(userSig);
        healthBus.loadBMIChart(info);
    }


    @When("^I load BMI$")
    public void iLoadBMI(BmiMS info) {
        info.setUserSig(userSig);
        healthBus.loadDataBmi(info);
    }




}