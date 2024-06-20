package stepdefinition;

import com.apis.globedr.business.imminization.ImmunizationBus;
import com.apis.globedr.business.system.SystemBus;
import com.apis.globedr.helper.Data;

import com.apis.globedr.model.request.immunization.VaccineInfo;
import com.apis.globedr.model.request.system.GetSystemPostRQ;
import com.apis.globedr.model.request.system.GetVacInfoRQ;
import com.apis.globedr.model.step.immunization.ImmunizationMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class ImmunizationStep extends Data {
    public ImmunizationStep() {
        super();
    }

    ImmunizationBus immuBus = new ImmunizationBus();
    SystemBus systemBus = new SystemBus();


    @When("^I get immunization by age$")
    public void getImmunizationByAge() {
        ImmunizationMS info = ImmunizationMS.builder().userSignature(userSig).build();
        immuBus.getImmunizationByAge(info);
    }

    @When("^I get immunization by vaccine$")
    public void getImmunizationByVaccine() {
        ImmunizationMS info = ImmunizationMS.builder().userSignature(userSig).build();
        immuBus.getImmunizationByVaccine(info);
    }

    @When("^I load medicines$")
    public void loadMedicines(List<String> vacIds) {
        ImmunizationMS info = ImmunizationMS.builder().userSignature(userSig).vacIds(vacIds).build();
        immuBus.loadMedicines(info);
    }

    @When("^I load vaccines list by \"([^\"]*)\" medIds and \"([^\"]*)\" vacId and \"([^\"]*)\" dose$")
    public void loadVaccineListByMed(String medId1, String vacId, String dose) {

        VaccineInfo vaccineInfo = VaccineInfo.builder().vacId(vacId).dose(dose).build();
        ImmunizationMS body = ImmunizationMS.builder()
                .medIds(Arrays.asList(medId1.split(",")))
                .vacList(Arrays.asList(vaccineInfo))
                .userSignature(userSig)
                .build();
        immuBus.loadVaccineByMed(body);

    }

    @When("^I get \"([^\"]*)\" vaccine information$")
    public void getVacInfo(String key) {
        GetVacInfoRQ body = GetVacInfoRQ.builder().key(key).build();
        systemBus.getVacInfo(body);
    }

    @When("^I get \"([^\"]*)\" system post information$")
    public void getSystemPostInfo(String postType) {
        GetSystemPostRQ body = GetSystemPostRQ.builder().build().setPostType(postType);
        systemBus.getSystemPost(body);
    }


    @Then("^Next vaccine group id should be \"([^\"]*)\"$")
    public void checkNextVaccineGroupId(String groupId) {
        String pathToIsNext = String.format("data.list[?(@.groupId== '%s')].isNext", groupId);
        ArrayList<Boolean> isNextList = response.extract(pathToIsNext);
        Assert.assertTrue(isNextList.get(0), "Next vaccine is not: " + groupId);
    }

    @Then("^\"([^\"]*)\" vaccine group id should have '(.*?)' shot$")
    public void vaccineGroupIdShouldHaveShot(String groupId, int expectedShot) {
        String pathToListShot = String.format("data.list[?(@.groupId== '%s')].listShot[*].categoryName", groupId);
        List<Object> shotList = response.extract(pathToListShot);
        Assert.assertEquals(shotList.size(), expectedShot, "Actual is: " + shotList.size() + " But expected is: " + shotList);
    }


    @When("^I update \"([^\"]*)\" vaccine id with \"([^\"]*)\" med and \"([^\"]*)\" dose below info$")
    public void updateVaccine(String vacId, String medId, String dose, ImmunizationMS body) {
        VaccineInfo vaccine = VaccineInfo.builder().dose(dose).vacId(vacId).medId(medId).build();
        body.setVacList(Arrays.asList(vaccine));
        body.setUserSignature(userSig);
        immuBus.updateVaccineRecord(body);
    }

    @When("^I get the information immunization$")
    public void getImmunizationInfo() {
        ImmunizationMS info = ImmunizationMS.builder().userSignature(userSig).build();
        immuBus.getImmunization(info);
    }

    @And("^The status \"([^\"]*)\" vaccine should be '(\\d+)' in \"([^\"]*)\" group name with dose is \"([^\"]*)\"$")
    public void checkVaccineStatus(String infoURL, int expectedStatus, String groupName, String dose) {
        String pathToStatus = String.format("data.list[?(@.groupName=='%s')].listShot[?(@.infoURL=='%s'&&@.dose=='%s')].status", groupName, infoURL, dose);
        List<Object> statusList = response.extract(pathToStatus);
        Assert.assertEquals(statusList.get(0), expectedStatus);
    }

    @When("^I remove immunization$")
    public void removeRecord(List<VaccineInfo> list) {

        ImmunizationMS body = ImmunizationMS.builder()
                .vacList(list)
                .userSignature(userSig)
                .build();
        immuBus.removeVaccineRecord(body);

    }


    @Then("^\"(.*?)\" group Id should have '(.*?)' shot$")
    public void checkVaccineRota(String groupId, int expectedShot) {
        String pathToListShot = String.format("data.list[?(@.groupId== '%s')].listShot[*]", groupId);
        List<Object> shotList = response.extract(pathToListShot);
        Assert.assertEquals(shotList.size(), expectedShot, "Actual is:  " + shotList.size() + "But exptected is:  " + shotList);
    }


    @And("^I view log book of Immunization$")
    public void iViewLogBookOfImmunization() {
        ImmunizationMS info = ImmunizationMS.builder().userSignature(userSig).build();
        immuBus.viewLogImmunization(info);
    }
}
