package stepdefinition;

import com.apis.globedr.apis.SponsorApi;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Data;
import com.apis.globedr.enums.SponsorStatus;
import com.apis.globedr.model.request.sponsor.DurationRQ;
import com.apis.globedr.model.response.sponsor.SponsorRS;
import io.cucumber.java.en.And;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SponsorStep extends Data {
    SponsorApi sponsorApi = SponsorApi.getInstant();

    private String startDate;
    private Integer sponsorId;
    private Integer total;

    @And("^I approve new sponsor for above org$")
    public void iApproveSponsorForOrgWithName() {
        sponsorApi.approveSponsor(orgSig);
        if(response.isSuccess()){
            orgSig = response.extract("data.info.org.sig");
            orgId = response.extract("data.info.org.id");
            sponsorId = response.extract("data.info.id");
        }

    }

    @And("^I update sponsor for org with status (active|inactive)$")
    public void iUpdateSponsorDorOrgWithStatusActive(String status) {
        sponsorApi.updateStatusForSponsor(orgSig, sponsorId, status);
    }

    @And("^admin load list of sponsor(| and get first org)$")
    public void iLoadListOfSponsor(String action, Map<String, Object> dataTable) {
        Map<String, Object> body = Common.getMap(dataTable);
        sponsorApi.loadListSponsor(body);
        if(action.contains("get")){
            orgSig = response.extract("data.list[0].org.sig");
        }
    }

    @And("^user load list of sponsor$")
    public void userLoadListOfSponsor(Map<String, Object> dataTable) {
        Map<String, Object> body = Common.getMap(dataTable);
        sponsorApi.loadListSponsor(body);
    }


    @And("^I select org with name \"([^\"]*)\" that have status (active|inactive)$")
    public void iSelectOrgWithNameThatHaveStatus(String name, String status) throws Throwable {
        sponsorApi.loadListSponsor();
        int total = response.extract(("data.totalCount"));
        double page = total / 10 + 1;
        boolean isExit = false;
        for (int i = 1; i <= page; i++) {
            sponsorApi.loadListSponsor(i);
            List<String> actualListName = response.extract("data.list[*].org.name");

            if (actualListName.contains(name)) {
                isExit = true;
                List<String> actualListStatus = response.extract(String.format("data.list[?(@.org.name=='%s')].status", name));
                Assert.assertEquals(actualListStatus.get(0), SponsorStatus.value(status) );
                break;
            }
        }
        Assert.assertTrue(isExit);

    }

    @And("I set duration for sponsor")
    public void iSetDurationForSponsor(DurationRQ info) {
        info.setOrgSig(orgSig);
        info.setId(sponsorId);
        sponsorApi.setTimeDuration(info);
    }

    @And("^I load list sponsor and check duration of org with name \"([^\"]*)\"$")
    public void iCheckDurationOfOrg(String name, DurationRQ info) {
        int diffTime = 120000;
        SponsorRS rs = sponsorApi.loadListSponsor().stream()
                .filter(s->s.getOrg().getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);

        Assert.assertTrue((info.getStartDateAsDate().getTime() - rs.getStartDateAsDate().getTime() <= diffTime ) &&
                (info.getEndDateAsDate().getTime() - rs.getEndDateAsDate().getTime() <= diffTime ));
    }

    @And("^I remove sponsor of org with name \"([^\"]*)\"$")
    public void iRemoveSponsorOfOrg(String name) {
        sponsorApi.deleteSponsor(sponsorId, orgSig);
    }

    @And("^admin load list of sponsor and I see sponsor of org \"([^\"]*)\"$")
    public void iLoadListOfSponsorAndISeeSponsorOrOrg(String name) {
        sponsorApi.loadListSponsor();
        total = response.extract(("data.totalCount"));
        double page = total / 10 + 1;
        boolean isExit = false;
        for (int i = 1; i <= page; i++) {
            sponsorApi.loadListSponsor(i);
            List<String> actualListName = response.extract("data.list[*].org.name");
            if (actualListName.contains(name)) {
                isExit = true;
                List<String> orgSigList = response.extract(String.format("data.list[?(@.org.name=='%s')].org.sig", name));
                orgSig = orgSigList.get(0);
                List<Integer> idList = response.extract(String.format("data.list[?(@.org.name=='%s')].id", name));
                sponsorId = idList.get(0);
                break;
            }
        }
        Assert.assertTrue(isExit);
    }

    @And("^Total sponsor decreases by one$")
    public void totalSponsorDecreasesByOne() {

    }

    @And("^Total sponsor decreases by '(\\d+)'$")
    public void totalSponsorDecreasesBy(int numberExpected) {
        sponsorApi.loadListSponsor();
        int totalCount = response.extract("data.totalCount");
        int numberActual = total - totalCount;
        Assert.assertEquals(numberActual, numberExpected);
    }

    @And("^User get list sponsor$")
    public void iGetListSponsor() {
        sponsorApi.getListSponsor();
    }

    @And("^I check sponsor return should be '(\\d+)'$")
    public void iCheckSponsorReturnShouldBe(int expected) {
        int actual = response.extract("data.totalCount");
        Assert.assertEquals(expected, actual);
    }


    @And("^I set duration for org with below info$")
    public void iSetDurationForOrgWithBelowInfo(Map<String, Object> dataTable) {
        sponsorApi.setTimeDuration(sponsorId, orgSig, dataTable);
    }


}
