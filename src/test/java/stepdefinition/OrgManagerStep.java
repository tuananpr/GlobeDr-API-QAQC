package stepdefinition;


import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.orgManager.OrgManagerBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.search.SysAdminSearchBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.orgManager.ContactOrgRQ;
import com.apis.globedr.model.step.org.OrgMS;
import com.apis.globedr.model.step.org.StaffMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import java.util.List;

public class OrgManagerStep extends Data {

    OrgBus orgBus = new OrgBus();
    OtherBus otherBus = new OtherBus();
    OrgManagerBus orgManagerBus = new OrgManagerBus();
    SysAdminSearchBus sysAdminSearchBus = new SysAdminSearchBus();
    @And("^On selected organization, the (TOTAL|MALE|FEMALE|UNSPECIFIED) members should be '(\\d+)'$")
    public void verifyMember(String genderType, int expectedMember) throws Exception {
        if (genderType.equals("TOTAL")) {
            int totalMember = response.extract("data.total");
            Assert.assertEquals(totalMember, expectedMember);
        } else {

        }
    }

    @Then("^The member list should( not)? contain$")
    public void checkMemberList(String isContain, List<String> expectedList) {
        List<String> ListPath = response.extract("data.list[*].displayName");
        Assert.assertTrue(ListPath.containsAll(expectedList));
    }


    @Then("^Current organization have '(\\d+)' groups$")
    public void checkNumberOfGroup(int expected) {
        int total = response.extract("data.total");
        Assert.assertEquals(total, expected);
    }


    @And("^I should have '(\\d+)' member with gender '(\\d+)'$")
    public void checkMemberWithGender(int gender, int expected) {
        int total = response.extract("data.total");
        total = total - 1;
        int dem = 0;
        List<Object> genderPath = response.extract("data.list[*].gender");
        for (int i = 0; i <= total; i++) {
            int y = (int) genderPath.get(i);
            if (y == gender) {
                dem = dem + 1;
                Assert.assertEquals(dem, expected);
            }
        }
    }

    @And("^I should have '(\\d+)' member with country \"([^\"]*)\"$")
    public void checkMemberWithCountry(int expected, String country) {
        int total = response.extract("data.total");
        total = total - 1;
        int dem = 0;
        List<String> countryPath = response.extract("data.list[*].country");
        for (int i = 0; i <= total; i++) {
            if (countryPath.get(i) == country) {
                dem = dem + 1;
                Assert.assertEquals(dem, expected);
            }

        }

    }


    @And("^I want to add the list specialty for organization \"([^\"]*)\"$")
    public void iWantToAddTheListSpecialtyForOrganization(String orgName, List<String> specialties) {
        OrgMS orgMS = new OrgMS();
        orgMS.setSpecialtyCodes(otherBus.loadSubSpecialtiesCode(specialties));
        orgMS.setOrgSig(orgManagerBus.getOrgSig(orgName));
        orgBus.addSpecialties(orgMS);
    }

    @And("^I want to remove the list specialty for organization \"([^\"]*)\"$")
    public void iWantToRemoveTheListSpecialtyForOrganization(String orgName, List<String> specialties) {
        String orgSig = orgManagerBus.getOrgSig(orgName);

        OrgMS orgMS = new OrgMS();
        orgMS.setSpecialtyCodes(orgBus.loadSpecialtiesCodeByName(orgSig, true, specialties));
        orgMS.setOrgSig(orgSig);
        orgBus.removeSpecialties(orgMS);
    }


    @When("^I change appointment type of org$")
    public void iChangeAppointmentTypeOfOrg(List<OrgMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgManagerBus.changeOrgAppointmentType(info);
        });
    }

    @And("^I get appointment type of org$")
    public void iGetAppointmentTypeOfOrg() {
        orgManagerBus.orgAppointmentType(orgSig);
    }


    @When("^On landing page, I send contact org with below info$")
    public void onLandingPageISendContactOrgWithBelowInfo(List<ContactOrgRQ> list) {
        list.forEach(orgManagerBus::contactOrg);
    }



}
