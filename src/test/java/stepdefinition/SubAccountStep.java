package stepdefinition;

import com.apis.globedr.business.ConnectionBus;
import com.apis.globedr.business.subAccount.AbsSubAccountBus;
import com.apis.globedr.business.subAccount.FamilyBus;
import com.apis.globedr.business.subAccount.SubAccountBus;
import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.apis.globedr.model.request.subAccount.ShareInfoRQ;
import com.apis.globedr.model.step.subAccount.SubAccountMS;
import com.apis.globedr.helper.Data;

import com.apis.globedr.stepdefinition.SqlDatabaseStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class SubAccountStep extends Data {
    public SubAccountStep() {
        super();
    }

    AbsSubAccountBus familyBus = new FamilyBus();
    ConnectionBus connectionBus = new ConnectionBus();


    @When("^I add new sub-account$")
    public void addSubAccount(List<SubAccountMS> list) {
        list.forEach(info ->{
            new SqlDatabaseStep().sqlDeleteUserByName(info.getDisplayName());
            familyBus.create(info);
        });
    }


    @And("I update profile for sub-account {string}")
    public void iUpdateProfileForSubAccount(String subAccountName, List<SubAccountMS> list) {
        list.forEach(sub -> {
            familyBus.update(subAccountName, sub);
        });
    }


    @When("^I switch main of (logged user|family members) with below info$")
    public void switchMainAccount(String workflow, SubAccountMS info) {
        info.setUserSig(userSig);
        AbsSubAccountBus bus = (workflow.contains("user")) ? new SubAccountBus() : new FamilyBus();
        userSig = bus.loads(info).get(0).getUserSignature();
    }

    @When("^I switch sub-account of (logged user|family members) with below info$")
    public void switchSubAccount(String workflow, List<SubAccountMS> list) {
        list.get(0).setUserSig(userSig);
        AbsSubAccountBus bus = (workflow.contains("user")) ? new SubAccountBus() : new FamilyBus();
        userSig = bus.getFirstSubAccount(list.get(0)).getUserSignature();
    }

    @When("^I load all account of (logged user|family members) with below info$")
    public void loadSubAccount(String workflow, SubAccountMS info) {
        info.setUserSig(userSig);
        AbsSubAccountBus bus = (workflow.contains("user")) ? new SubAccountBus() : new FamilyBus();
        bus.loads(info);
    }

    @When("^I get sub-account of family members with below info$")
    public void getSubAccountInFamily(List<SubAccountMS> list) {
        list.get(0).setUserSig(userSig);
        subSig = familyBus.getFirstSubAccount(list.get(0)).getUserSignature();
    }


    @When("^I remove a above sub-account with name \"([^\"]*)\"$")
    public void iRemoveAAboveSubAccount(String name) {
        familyBus.remove(name);
    }

    @When("^I add (\\d+) sub-account$")
    public void iAddSubAccount(int total) {
        SubAccountMS person = SubAccountMS.builder()
                .displayName("Minh_Béo")
                .dob("2018-09-30T00:00:00.000")
                .carerType(1)
                .country(new Country("VN", "Việt Nam", "84"))
                .city(new City("HCM", "Hồ Chí Minh"))
                .district(new District("1444", "Quận 3"))
                .ward(new Ward("20311", "Phường 11"))
                .gender(1)
                .height(165.0)
                .weight(65.0).build();

        for (int index = 1; index <= total; index++) {
            familyBus.create(person);
        }

    }


    @And("^I load list shared account$")
    public void iLoadListSharedAccount(List<SubAccountMS> list) {
        list.forEach(familyBus::listSharedAccount);
    }


    @And("^I have '(\\d+)' main_account$")
    public void iHaveMain_account(int expected) {
        int actual = response.extract("data.totalCount");
        Assert.assertEquals(actual, expected);
    }

    @And("^I refuses to receive sub-account \"([^\"]*)\"$")
    public void iDeleteSub_accountInMySharedAccount(String name) {
        familyBus.sharedAccount(name);
    }


    @And("^I have '(\\d+)' count shared$")
    public void checkCountShared(int expected) {
        List<Object> actualList = response.extract("$..sharedPersons[*]");
        Assert.assertEquals(actualList.size(), expected);
    }




    @And("^I (share|unshare) my sub-account to friends$")
    public void shareSubAccountToFriends$(String shareType, List<SubAccountMS> list) {
        list.forEach(familyBus::shareToConnection);
    }

    @And("^I load friends to share my account$")
    public void loadFriendsToShare(SubAccountMS info) {
        familyBus.loadConnectionToShare(info);
    }


    @And("^I (share|unshare) my sub-account to org")
    public void shareSubAccountToOrgs(String shareType, List<SubAccountMS> list) {
        list.forEach(info -> {
            List<ShareInfoRQ> shareInfos = new ArrayList<>();
            connectionBus.loadFollowOrgsByName(info.getSharedConnectionName()).forEach(
                    org -> {
                        shareInfos.add(ShareInfoRQ.builder()
                                .orgSig(org.getOrgSig())
                                .userManageType(info.getSharedType())
                                .build());
                    });

            familyBus.shareToConnection(info.getName(), shareInfos);
        });

    }




}
