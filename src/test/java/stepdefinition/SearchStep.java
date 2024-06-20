package stepdefinition;

import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.search.SearchBus;
import com.apis.globedr.business.search.SysAdminSearchBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Wait;
import com.apis.globedr.model.response.org.LoadOrgsRS;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.helper.Data;
import com.apis.globedr.services.es.ElasticSearchApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.*;

public class SearchStep extends Data {


    SearchBus userSearchBus = new UserSearchBus();
    SearchBus sysAdminSearchBus  = new SysAdminSearchBus();
    ElasticSearchApi elasticSearchApi = new ElasticSearchApi();
    OtherBus otherBus = new OtherBus();

    @Then("^The result list should return (PROVIDER|ORGANIZATION)$")
    public void verifyResultType(String type) {
        Boolean isOrg = type.toUpperCase().equals("ORGANIZATION");
        List<Boolean> isOrgList = response.extract("data.list[*].isOrg");
        for (Boolean item : isOrgList) {
            Assert.assertEquals(item, isOrg);
        }
    }

    private Boolean checkListIncresing(List<Double> checkList) {
        for (int i = 0; i < checkList.size() - 1; i++) {
            DecimalFormat df = new DecimalFormat("#.######");
            Double a = Double.valueOf(df.format(checkList.get(i)));
            Double b = Double.valueOf(df.format(checkList.get(i + 1)));
            if (a > b) {
                return false;
            }
        }
        return true;
    }

    @Then("^The distance should be (INCREASE|DECREASE)$")
    public void checkDistance(String distance) {
        if (distance.toUpperCase().equals("INCREASE")) {
            List<Double> distanceList = response.extract("data.list[*].kmDistance");
            if (distanceList.size() > 0) {
                Assert.assertTrue(checkListIncresing(distanceList));
            }
        }
    }

    @Then("^The specialty list returned should include \"([^\"]*)\"$")
    public void checkSpecialtyList(String spec) throws Exception {
        List<List<String>> specialtyList = response.extract("data.list[*].specialties");
        if (specialtyList.size() > 0) {
            for (List<String> item : specialtyList) {
                Assert.assertTrue(item.contains(spec), spec + " asshole");
            }
        } else {
            throw new Exception(">>>>> Specialty List is empty");
        }
    }

    @Then("^The result list should( not)? contain$")
    public void checkResultList(String isContain, List<String> expectedResult) {
        if (isContain == null) {
            List<String> actualResult = response.extract("data.list[*].name");
            for (String name : expectedResult) {
                Assert.assertTrue(actualResult.contains(name), ">>>>>> Không có " + name + " trong list trả về");
            }
        }
    }


    @And("^As User, I search org$")
    public void userSearchMedicalOrg(OrgMS info) {
        userSearchBus.loadOrgs(info);
    }

    @When("^As sysAdmin, I search org$")
    public void searchOrg(OrgMS info) {
        List<LoadOrgsRS> rs = sysAdminSearchBus.loadOrgs(info);
        if (response.isSuccess()) {
            orgSig = rs.get(0).getOrgSig();
            orgId = rs.get(0).getOrgId();
        }
    }


}
