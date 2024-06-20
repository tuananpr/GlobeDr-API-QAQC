package stepdefinition;

import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.branch.BranchMS;
import com.apis.globedr.model.step.branch.UpdateBranchMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.util.List;

public class BranchSteps extends Data {

    OrgBus orgBus = new OrgBus();
    OtherBus otherBus = new OtherBus();

    @And("On above org, I load branch")
    public void onAboveOrgILoadBranch() {
        orgBus.loadBranch(orgSig);
    }

    @And("On above org, I delete branch name {string}")
    public void onAboveOrgIDeleteBranchName(String name) {
        BranchMS branchMS = new BranchMS();
        branchMS.setOrgSig(orgSig);
        branchMS.setName(name);
        orgBus.removeBranch(branchMS);
    }

    @And("On above org, I update branch name {string} with below info")
    public void onAboveOrgIUpdateBranchNameWithBelowInfo(String branchName, UpdateBranchMS newInfo) {
        BranchMS old = new BranchMS();
        old.setName(branchName);
        old.setOrgSig(orgSig);
        orgBus.updateBranch(old, newInfo);
    }


    @When("^On above org, I create a branch$")
    public void createBranch(List<BranchMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            orgBus.createBranch(info);
        });
    }


}