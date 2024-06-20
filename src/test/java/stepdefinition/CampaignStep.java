package stepdefinition;

import com.apis.globedr.helper.Data;
import com.apis.globedr.business.campaign.*;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.*;

public class CampaignStep extends Data {
    public String msgSig;

    AbsCampaign campaignBus = new CampaignNormalBus();

    @Then("^I should have '(\\d+)' campaigns in my conversation list$")
    public void verifyNumberOfCampaign(int expected) {
        List<Object> actualList = response.extract("data.list[*]");
        Assert.assertEquals(actualList.size(), expected);
    }


    private void createCampaign(AbsSendMessage campaign, ChatMS body) {
        body.setSenderSig(orgSig);
        SendMessageRS msg = campaign.send(body);
        msgSig = msg.getMsgSig();
    }

    @And("I create campaign")
    public void iCreateCampaign(List<ChatMS> campaigns) {
        campaigns.forEach(info -> {
            createCampaign(new CampaignNormalBus(), info);
        });
    }

    @When("^I load campaign list$")
    public void loadCampaigns(List<ChatMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            campaignBus.loadCampaigns(info);
        });

    }


    @And("I create voucher campaign")
    public void iCreateVoucherCampaign(List<ChatMS> campaigns) {
        campaigns.forEach(info -> {
            createCampaign(new CampaignVoucherBus(), info);
        });
    }

    @And("^I create article campaign$")
    public void iCreateArticleCampaign(List<ChatMS> campaigns) {
        campaigns.forEach(info -> {
            createCampaign(new CampaignArticleBus(), info);
        });
    }

    @And("^I create topdeal campaign$")
    public void iCreateTopdealCampaign(List<ChatMS> campaigns) {
        campaigns.forEach(info -> {
            createCampaign(new CampaignTopdealBus(), info);
        });
    }



    @When("^I load all user that unclick on campaign$")
    public void iLoadAllUserThatUnclickedCampaign(List<ChatMS> list) {
        ChatMS campaign = list.get(0);
        campaign.setOrgSig(orgSig);
        campaignBus.loadUserUnClickCampaign(campaign);
    }

    @When("^I load all user that unreal on campaign$")
    public void iLoadAllUserThatUnrealCampaign(List<ChatMS> list) {
        ChatMS campaign = list.get(0);
        campaign.setOrgSig(orgSig);
        campaignBus.loadUserUnreadCampaign(campaign);
    }

    @When("^I load all user that click on campaign$")
    public void iLoadAllUserThatClickedCampaign(List<ChatMS> list) {
        ChatMS campaign = list.get(0);
        campaign.setOrgSig(orgSig);
        campaignBus.loadUserClickCampaign(campaign);
    }


}
