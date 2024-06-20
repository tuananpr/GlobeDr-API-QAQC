package stepdefinition.healthStep;

import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.health.GrowthChartMS;
import io.cucumber.java.en.And;

public class HealthGrowthChartStep extends Data {
    HealthBus healthBus = new HealthBus();

    @And("^I load growth chart$")
    public void loadGrowthChart(GrowthChartMS info) {
        info.setUserSIg(userSig);
        healthBus.loadGrowthChart(info);
    }


}
