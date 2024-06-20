package stepdefinition;

import com.apis.globedr.business.userManager.UserManagerBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.request.userManager.LoadUsersRQ;
import com.apis.globedr.model.step.userManager.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.List;

public class UserManagerStep extends Data {

    UserManagerBus userManagerBus = new UserManagerBus();

    @And("^I set default password for the below username$")
    public void iSetDefaultPasswordForTheBelowUsername(List<PwdResetMS> list) {
        list.forEach(userManagerBus::pwdReset);
    }

    @When("^As sysAdmin, I load user$")
    public void iLoadUser(List<LoadUsersRQ> list) {
        list.forEach(userManagerBus::loadUsers);
    }


    @When("^As sysAdmin, I verify below username$")
    public void onSystemIVerifyForBelowUser(List<VerifyUserMS> list) {
        list.forEach(userManagerBus::verifyUser);
    }

    @When("^As sysAdmin, I remove below username$")
    public void onSystemIRemoveBelowUser(List<RemoveUserMS> list) {
        list.forEach(userManagerBus::removeUsers);

    }

    @When("As sysAdmin, I change status of below user")
    public void onSystemIChangeStatusOfBelowUserTo(List<ChangeUserStatusMS> list) {
        list.forEach(userManagerBus::changeUserStatus);
    }

    @And("As sysAdmin, I gift point to user")
    public void sysAdminGiftPointToUser(List<GiftPointMS> list) {
        list.forEach(info ->{
            info.setUserSigs(userManagerBus.loadUsersAndGetSigs(info.getUser()));
            userManagerBus.giftPoint(info);
        });
    }



    @When("As sysAdmin, I set user function")
    public void asSysAdminISetUserFunction(List<UserFeatureAttributeMS> list) {
        list.forEach(userManagerBus::changeUserFeatureAttribute);
    }

    @And("As sysAdmin, I load point rules")
    public void asSysAdminILoadRewardPoints(PointRuleMS info) {
        userManagerBus.pointRules(info);
    }

    @And("As sysAdmin, I create point rules")
    public void asSysAdminICreateRewardPoints(List<PointRuleMS> list) {
        list.forEach(info ->{
            userManagerBus.newPointRule(info);
        });

    }

    @And("As sysAdmin, I load point history of all user")
    public void iLoadUsedPointsHistoryOfAllUser(FilterDate info) {
        userManagerBus.usedPointHistory(info);
    }

}
