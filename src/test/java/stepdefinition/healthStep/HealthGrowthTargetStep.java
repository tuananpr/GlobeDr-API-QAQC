package stepdefinition.healthStep;

import com.apis.globedr.business.account.AccountBus;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.*;
import com.apis.globedr.model.step.health.*;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class HealthGrowthTargetStep extends Data {

    AccountBus accountBus = new AccountBus();
    HealthBus healthBus = new HealthBus();
    GrowthTargetMS growthTargetMS;



    @When("^I add growth target$")
    public void addGrowthTarget(List<GrowthTargetMS> list) {
        list.forEach(info->{
            info.setUserSig(userSig);
            healthBus.updateGrowthTarget(info);

            growthTargetMS = info;
        });
    }

    @And("^The growth target should be \"([^\"]*)\"$")
    public void theTimeWantToShow(String growthTargetAgename) {
        growthTargetMS.updateGrowthTarget(growthTargetAgename);
        Assert.assertTrue(response.extract("data.info.growthTargetAgeName").toString().equalsIgnoreCase(growthTargetAgename));
        Assert.assertEquals(response.extract("data.info.heightTarget"), growthTargetMS.getHeightTarget());
        Assert.assertEquals(response.extract("data.info.weightTarget"), growthTargetMS.getWeightTarget());
    }

    @Then("^Check result with gender \"(.*?)\"$")
    public void checkGrowthTargetByGender(String genDer) {
        double showHight = response.extract("data.info.heightTarget");
        int gender = accountBus.getPersonalInfo().getGender();

        int temp = genDer.equals("BOY") ? 1 : 2;

        Assert.assertEquals(temp, gender);
        switch (genDer) {
            case "BOY":
                Assert.assertEquals((growthTargetMS.getHeightMother() +  growthTargetMS.getHeightFather() + 13) / 2, showHight);

                break;
            case "GIRL":
                Assert.assertEquals( (growthTargetMS.getHeightMother() +  growthTargetMS.getHeightFather() - 13) / 2, showHight);
                break;
        }

    }


    @And("^I load growth target chart$")
    public void loadGrowthTargetChart(GrowthTargetMS body) {
        healthBus.loadGrowthTarget(body);
    }

    @And("^I load growth target chart full$")
    public void loadGrowthTargetChartfull(GrowthTargetMS info) {
        healthBus.loadGrowthTargetFull(info);
    }





}