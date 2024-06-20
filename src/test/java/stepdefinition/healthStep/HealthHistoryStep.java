package stepdefinition.healthStep;

import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.helper.Path;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.HtmlFile;
import com.apis.globedr.model.response.health.healthHistory.QuestionItemsRS;
import com.apis.globedr.model.step.health.healthHistory.AnswerDataFactory;
import com.apis.globedr.model.step.health.healthHistory.HealthHistoryInfoMS;
import com.apis.globedr.model.step.health.healthHistory.HealthHistoryMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.List;

public class HealthHistoryStep extends Data {

    HealthBus healthBus = new HealthBus();

    @Then("^I load health history$")
    public void loadHealthHistory(HealthHistoryMS info) {
        info.setUserSig(userSig);
        healthBus.healthHistory(info);
    }

    @And("^I update health history information$")
    public void iLoadHealthHistoryInformation(List<HealthHistoryInfoMS> list) {
        healthBus.healthHistoryInfo(userSig, list);
    }

    @Then("Health history information should match below info")
    public void healthHistoryInformationShouldMatchBelowInfo(List<HealthHistoryInfoMS> list) {
        list.forEach(expected ->{
            expected.setUserSig(userSig);
            QuestionItemsRS actual = healthBus.getQuestionItem(expected);
            Assert.assertEquals(AnswerDataFactory.init(actual).toString(), expected.getAnswerData().toString(),
                    String.format("At Group type '%s' question name '%s' Item name '%s' has answerData",
                            expected.getGroupType(), expected.getQuestionName(), expected.getItemName()));
        });
    }


    @And("^I print health history$")
    public void iPrintHealthHistory() {
        HealthHistoryMS info = HealthHistoryMS.builder().userSig(userSig).build();
        healthBus.printHealthHistory(info);
    }

    @And("^I export health history to file \"([^\"]*)\"$")
    public void iExportHealthHistory(String path) {
        HealthHistoryMS info = HealthHistoryMS.builder().userSig(userSig).build();
        String content = String.join("\n", healthBus.printHealthHistory(info).getPages());
        File actual = new HtmlFile(content).convertToPdf();
        actual.save( Path.TARGET  + path);
    }

    @And("^I print health history and it should match with \"([^\"]*)\"$")
    public void theActualPdfOfHealthHistoryShouldBeMatchedWith(String expectedFile) {
        String actualFile = Path.TARGET + "actualHealthHistory.pdf";
        String resultFile = Path.TARGET + "resultHealthHistory";

        String content = String.join("\n", healthBus.printHealthHistory(userSig).getPages());

        File actual = new HtmlFile(content).convertToPdf();
        File expect = FileFactory.getFile(expectedFile);
        actual.save(actualFile);

        Assert.assertTrue(actual.compare(expect, resultFile),
                String.format("Please check result of health history after compare at path '%s.pdf' ", resultFile));

    }



}