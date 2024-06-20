package stepdefinition;

import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.orgManager.OrgManagerBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.business.search.SysAdminSearchBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.enums.*;
import com.apis.globedr.enums.Currency;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.org.ConsultConfigRQ;
import com.apis.globedr.model.request.org.ConsultConfigValueRQ;
import com.apis.globedr.model.response.org.OrgRS;
import com.apis.globedr.model.response.org.OrgsManageRS;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.org.*;
import com.apis.globedr.model.step.review.ReviewMS;
import com.apis.globedr.services.authorization.Token;
import com.apis.globedr.services.es.ElasticSearchApi;
import com.apis.globedr.stepdefinition.SqlDatabaseStep;
import com.rest.core.response.Response;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class OrgSteps extends Data {

    OrgManagerBus orgManagerBus = new OrgManagerBus();
    OrgBus orgBus = new OrgBus();
    OtherBus otherBus = new OtherBus();
    UserSearchBus userSearchBus = new UserSearchBus();
    SysAdminSearchBus adminSearchBus = new SysAdminSearchBus();
    ElasticSearchApi elasticSearchApi = new ElasticSearchApi();


    @When("^I create a org$")
    public void createOrg(List<CreateOrgMS> list) {
        list.forEach(info -> {
            OrgRS rs = orgBus.createOrg(info);
            if(response.isSuccess()) orgSig = rs.getOrgSig();
        });
    }


    @And("^I select org \"([^\"]*)\" that I manage$")
    public void switchOrgManageList(String name) {
        OrgsManageRS orgsManageRS = orgBus.selectOrgManage(name);
        Assert.assertNotNull(orgsManageRS.getOrgSig(), String.format("This account not manage org '%s'", name));
        orgSig = orgsManageRS.getOrgSig();
        orgId = orgsManageRS.getOrgId();
    }

    @And("^I get information org$")
    public void iGetInformationOrg(OrgMS info) {
        orgBus.getOrgInfo(userSearchBus.loadOrgsByName(info.getName()).get(0).getSig());
    }

    @And("^I get information branch")
    public void iGetInformationBranch(OrgMS info) {
        String branchSig = orgBus.loadBranch(userSearchBus.loadOrgsByName(info.getName()).get(0).getSig()).get(0).getOrgSig();
        orgBus.getOrgInfo(branchSig);

    }

    @And("^organization was followed$")
    public void organizationWasFollowed() {
        boolean result = response.extract("data.orgInfo.isFollowed");
        Assert.assertTrue(result);
    }

    @And("^When without attributes for org then results return is \"([^\"]*)\"$")
    public void checkResultsReturnIs(Boolean key) {
        Boolean result = response.extract("data.orgInfo.allowOrder");
        Assert.assertEquals(key, result);
    }

    @And("^When set attributes for org then results return is \"([^\"]*)\"$")
    public void whenSetAttributesForOrgThenResultsReturnIs(Boolean key) {
        Boolean result = response.extract("data.orgInfo.allowOrder");
        Assert.assertEquals(key, result);
    }

    @And("^I check request after add voucher$")
    public void iCheckRequestAfterAddVoucher() {
        List<Boolean> result = response.extract("data.list[*].hasVoucher");
        Assert.assertTrue(result.contains(true));
    }


    @Then("^I should have '(\\d+)' product$")
    public void iShouldHaveProduct(int number) {
        Assert.assertEquals(response.extractAsList("data.list[*]").size(), number);
    }


    @And("^I check attribute of organization name \"(.*?)\"$")
    public void iCheckAttributeOfOrganization(String orgName, List<String> attributeList) {
        String orgSig = adminSearchBus.loadOrgsByName(orgName).get(0).getOrgSig();
        orgBus.getOrgAttributes(orgSig);
        Assert.assertEquals(response.extractAsInt("data.orgAttributes"), OrgAttributes.convert(attributeList));
    }


    private void addStaff(String orgSig, List<StaffMS> list) {
        list.forEach(staffMS -> {
            staffMS.setOrgSig(orgSig);
            addStaff(staffMS);
        });
    }

    private void addStaff(StaffMS staffMS) {
        orgBus.addStaffs(staffMS);
        if (staffMS.getIsManager() != null) orgBus.setOrgManager(staffMS, staffMS.getIsManager());
        if (staffMS.getIsAdmin() != null) orgBus.setOrgAdmin(staffMS, staffMS.getIsAdmin());
        if (staffMS.getIsTelemedicine() != null) orgBus.setTelemedicine(staffMS, staffMS.getIsTelemedicine());
        if (staffMS.getIsTrial() != null) orgBus.userTrialAdd(staffMS);
        if (staffMS.getIsProvider() != null && staffMS.getIsProvider()) orgBus.setProvider(staffMS);
    }

    @When("On org, I add staff")
    public void onOrgIAddStaff(List<StaffMS> list) {
        addStaff(orgSig, list);
    }


    @When("On org, I remove user trial")
    public void onOrgIRemoveUserTrial(List<StaffMS> list) {
        list.forEach(staffMS -> {
            staffMS.setOrgSig(orgSig);
            orgBus.userTrialRemove(staffMS);
        });
    }

    @When("As sysAdmin, I add staff for org name {string}")
    public void onOrgIAddStaff(String orgName, List<StaffMS> list) {
        orgSig = adminSearchBus.loadOrgsByName(orgName).stream()
                .filter(o -> o.getOrgName().equalsIgnoreCase(orgName))
                .map(o -> o.getOrgSig())
                .findFirst().orElse(null);
        addStaff(orgSig, list);
    }


    @And("I load staffs of department")
    public void iLoadStaffsOfDepartment(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setIsAdminLoad(true);
            orgBus.loadStaffs(info);
        });

    }


    @When("^I remove staffs with name$")
    public void iRemoveStaffsWithName(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.removeStaffs(info);
        });
    }

    @And("^I change org type$")
    public void iChangeOrgTypeForOrg(OrgMS info) {
        info.setOrgSig(orgManagerBus.loadOrgsByName(info.getName()).get(0).getOrgSig());
        orgBus.updateOrgType(info);
    }

    @And("^I add default image number '(\\d+)' for org$")
    public void iAddDefaultImageForOrg(int imgId) {
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).imgId(imgId).build();
        orgBus.defaultCover(orgMS);
    }

    @And("^I update introduction for organization with description \"([^\"]*)\"$")
    public void iUpdateIntroductionForOrganization(String intro) {
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).intro(intro).build();
        orgBus.updateIntro(orgMS);
    }


    @And("^I update information for organization below info$")
    public void iUpdateInformationForOrganizationBelowInfo(List<OrgMS> list) {
        list.forEach(info -> {
            info.setOrgId(orgId);
            info.setOrgSig(orgSig);
            orgBus.updateOrg(info);
        });


    }

    @Then("^The response return should be image$")
    public void theResponseReturnShouldBeImage() {
        String cover = response.extract("data.orgCoverUrl");
        Assert.assertNotNull(cover);
    }


    @And("^I get feature attribute for organization$")
    public void iGetFeatureAttributeForOrganization() {
        orgBus.featureAttributes(orgSig);
    }


    @When("^On org, I load rating$")
    public void onOrgILoadRating(ReviewMS info) {
        String orgSig = adminSearchBus.loadOrgsByName(info.getOrgName()).get(0).getOrgSig();
        info.setOrgSig(orgSig);
        orgBus.loadRating(info);
    }

    @And("^I load all department of org above$")
    public void iLoadDepartmentOfOrgAbove() {
        orgBus.loadDepartment(OrgMS.builder().orgSig(orgSig).build());
    }


    @When("^I add department on org$")
    public void iAddDepartmentWithNameForOrg(List<DepartmentMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.addDepartment(info);
        });
    }

    @When("^I remove department on org$")
    public void iRemoveDepartmentForOrg(List<DepartmentMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.removeDepartment(info);
        });
    }


    @Then("^I have '(\\d+)' staff in dept$")
    public void iHaveStaffToOtherDept(int number) {
        int countStaff = response.extract("data.totalCount");
        Assert.assertEquals(countStaff, number);
    }

    @And("^I move staff to above department$")
    public void iMoveStaffToAboveDepartment(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.moveStaffs(info);
        });
    }

    @And("^On org, I set hide staff$")
    public void iSetHideStaffWithName(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.hideStaffs(info);
        });
    }

    @Then("^The response return user \"([^\"]*)\" (is|is not) hide$")
    public void theResponseReturnUserIsHide(String name, String value) {
        if (response != null) {
            List<Boolean> content = response.extract(String.format("data.list[?(@.userName == '%s')].isHidden", name));
            if (value.equalsIgnoreCase("is")) {
                Assert.assertTrue(content.get(0));
            } else {
                Assert.assertFalse(content.get(0));
            }
        } else {
            Assert.fail("response is null");
        }
    }

    @Then("^The response return user \"([^\"]*)\" (is|is not) doctor$")
    public void theResponseReturnUserIsDoctor(String name, String value) {
        if (response != null) {
            List<Boolean> content = response.extract(String.format("data.list[?(@.userName == '%s')].isProvider", name));
            if (value.equalsIgnoreCase("is")) {
                Assert.assertTrue(content.get(0));
            } else {
                Assert.assertFalse(content.get(0));
            }
        } else {
            Assert.fail("response is null");
        }
    }


    @Then("^The list staff should(not)? be name$")
    public void theListStaffShouldBeName(String isContent, List<String> expectedList) {
        Assert.assertEquals(response.extractAsList("data.list[*].userName").containsAll(expectedList),
                isContent == null);
    }

    @And("^I refresh api key$")
    public void iRefreshApiKey() {
        orgBus.refreshApiKey(new OrgSig(orgSig));
        apikey = response.extract("$.data.apiKey");
    }


    @And("^I update cover \"([^\"]*)\" for above org$")
    public void iUpdateCoverForAboveOrg(String pathFile) {
        OrgMS orgMS = new OrgMS();
        orgMS.setOrgSig(orgSig);
        orgMS.setFile(pathFile);
        orgBus.cover(orgMS);
    }


    @When("^On org, I create new account on department$")
    public void onOrgICreateNewDoctorForAboveDepartment(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.newDoctor(info);
        });
    }


    @And("^I select staff name \"([^\"]*)\" into department$")
    public void iSelectStaffNameIntoDepartment(String name) {
        doctorSig = response.extractAsList(String.format("data.list[?(@.userName == '%s')].userSig", name)).get(0);
    }


    @When("^I change UI Type of org is \"([^\"]*)\"$")
    public void iChangeUITypeOfOrgIs(String orgUIType) {
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).orgUIType(orgUIType).build();
        orgBus.uiType(orgMS);
    }


    @And("I set features for staff")
    public void iSetTheBelowFeaturesForStaffName(List<StaffMS> list) {
        list.forEach(staffMS -> {
            staffMS.setOrgSig(orgSig);
            orgBus.setFeaturesForStaff(staffMS);
        });
    }

    @And("I get delivery types of org")
    public void iGetDeliveryTypesOfOrg() {
        orgBus.deliveryTypes(new OrgSig(orgSig));
    }


    @And("^I set org currency is (VND|USD|None)$")
    public void iSetOrgCurrencyIsVND(String currencyType) {
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).currency(Currency.value(currencyType)).build();
        orgBus.setCurrency(orgMS);
    }

    @And("^(User|Manage|WebManage) access to staff with name is \"(.*?)\" into department is \"(.*?)\"$")
    public void iAccessToStaffWithNameIsIntoDepartmentIs(String appType, String staffName, String departmentName) {
        StaffMS staffMS = StaffMS.builder()
                .orgSig(orgSig)
                .displayName(staffName)
                .deptName(departmentName)
                .appType(AppType.value(appType)).deviceId(AccountMS.getDefaultDeviceId()).build();
        Response rs = orgBus.accessStaff(staffMS);

        Token.getInstance().save(
                rs.extract("data.accessToken"),
                rs.extract("data.refreshToken"),
                rs.extract("data.tokenType")
        );

    }

    @And("^On org, I change password staff$")
    public void onOrgIChangePasswordForAboveDoctor(List<StaffMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.pwdDoctor(info);
        });
    }


    @And("On org, I get feature attribute of staff")
    public void onOrgIGetFeatureAttributeOfStaff() {
        OrgMS body = new OrgMS();
        body.setOrgSig(orgSig);
        orgBus.orgFeatureAttributeForStaff(body);
    }

    @And("^I set the below feature attribute for org$")
    public void iSetTheBelowFeatureAttributeForOrg(List<String> attributeList) {
        OrgMS orgMS = OrgMS.builder().orgSig(orgSig).orgFeatureAttributes(OrgFeatureAttributes.convert(attributeList)).build();
        orgManagerBus.orgFeatureAttribute(orgMS);
    }


    @And("^On Web, I open org \"([^\"]*)\"$")
    public void onWebIOpenOrg(String name) {
        orgBus.getOrgInfo(orgManagerBus.loadOrgsByName(name).get(0).getOrgSig());
        orgSig = response.extract("data.orgInfo.orgSig");
    }

    @And("^I get list specialty of org$")
    public void iGetListSpecialtyOfOrg() {
        orgBus.loadSpecialties(orgSig, true);
    }


    @And("I re-create a org with full of feature")
    public void iReCreateAOrgWithFullOfFeature(List<CreateOrgMS> list) {
        list.forEach(org -> {
            org.updateDefaultForNullField();
            //OrgMS org = info.updateFrom(OrgMS.initDetault(info.getName()));
            //org.setType(OrgType.value(org.getType()));
            // turn off to send sms after patient book appointment (Cost Saving)
            //org.setOrgAttribute(OrgAttributes.JoinedGdr.value() + OrgAttributes.EnableSendSMSAppointment.value());
            if (org.getOrgAttribute() == null) org.setOrgAttribute(OrgAttributes.JoinedGdr.value());
            if (org.getOrgFeatureAttributes() == null) org.setOrgFeatureAttributes(OrgFeatureAttributes.ALL.value());
            if (org.getCurrency() == null) org.setCurrency(Currency.VND.value());


            // delete org
            new SqlDatabaseStep().deleteOrganization(org.getName());

            // create org.
            orgSig = orgBus.createOrg(org).getOrgSig();
            org.setOrgSig(orgSig);


            orgManagerBus.changeOrgAttribute(org).mustSucceed();
            orgManagerBus.orgFeatureAttribute(org).mustSucceed();
            orgBus.setCurrency(org).mustSucceed();

            org.getSpecialties().forEach(name -> {
                orgBus.addSpecialties(org.getOrgSig(), otherBus.loadSubSpecialtiesCode(org.getSpecialties())).mustSucceed();
            });

            org.allStaff().forEach(name -> {
                orgBus.addStaffs(new StaffMS(org.getOrgSig(), name, org.getCountry().getCountry())).mustSucceed();
            });

            org.getOwner().forEach(name -> {
                orgBus.setOrgManager(new StaffMS(org.getOrgSig(), name, org.getCountry().getCountry()), true).mustSucceed();
            });

            org.getAdmin().forEach(name -> {
                orgBus.setOrgAdmin(new StaffMS(org.getOrgSig(), name, org.getCountry().getCountry()), true).mustSucceed();
            });

            org.getDoctor().forEach(name -> {
                orgBus.setProvider(new StaffMS(org.getOrgSig(), name, org.getCountry().getCountry()));
            });

            org.getDoctorTelemedicine().forEach(name -> {
                orgBus.setTelemedicine(new StaffMS(org.getOrgSig(), name, org.getCountry().getCountry()), true).mustSucceed();
            });
        });

    }


    @And("As manager, I setting consult")
    public void asManagerISettingConsult(ConsultConfigRQ info) {
        info.setOrgSig(orgSig);
        orgBus.consultConfig(info);
    }

    @And("As manager, I get consult settings")
    public void asManagerIGetConsultSettings() {
        ConsultConfigValueRQ info = ConsultConfigValueRQ.builder().orgSig(orgSig).build();
        orgBus.consultConfigValue(info);
    }


    @And("As manager, I checks if the customer care answering conversation or not")
    public void asManagerIChecksIfTheCustomerCareAnsweringConversationOrNot(StaffMS info) {
        info.setOrgSig(orgSig);
        orgBus.checkPickUpChat(info);
    }
}