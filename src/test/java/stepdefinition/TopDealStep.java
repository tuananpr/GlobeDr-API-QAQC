package stepdefinition;

import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.business.topdeal.TopDealBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.request.topdeal.UploadPromotionFileRQ;
import com.apis.globedr.model.request.topdeal.UploadPromotionIconRQ;
import com.apis.globedr.model.step.topdeal.PromotionMS;
import com.apis.globedr.model.step.topdeal.TopDealMS;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import java.util.List;

public class TopDealStep extends Data {

    TopDealBus topDealBus = new TopDealBus();
    UserSearchBus userSearchBus = new UserSearchBus();

    @Given("^I create TopDeal$")
    public void iCreateTopDeal(List<TopDealMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            topDealBus.addTopDeal(info);
        });
    }

    @When("^As manager, I load TopDeal$")
    public void onOrgILoadTopdeal(TopDealMS info) {
        info.setOrgSig(orgSig);
        topDealBus.loadTopDeals(info);
    }

    @And("^I add promotion for TopDeal \"([^\"]*)\"$")
    public void iAddPromotionForAboveTopdeal(String name, List<PromotionMS> list) {
        TopDealMS topDeal = TopDealMS.builder().orgSig(orgSig).name(name).build();
        String topDealSig = topDealBus.getTopDealSig(topDeal);
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            info.setTopDealSig(topDealSig);
            topDealBus.addPromotion(info);
        });
    }

    @And("^I upload image for promotion \"([^\"]*)\" of TopDeal \"([^\"]*)\"$")
    public void iUploadImageForPromotion(String promotionName, String topDealName, UploadPromotionIconRQ info) {
        String promotionSig = topDealBus.getPromotionSig(orgSig, topDealName, PromotionMS.builder().name(promotionName).build());
        info.setPromotionSig(promotionSig);
        topDealBus.uploadPromotionIcon(info);
    }

    @And("^I upload attachment file for promotion \"([^\"]*)\" of TopDeal \"([^\"]*)\"$")
    public void iUploadAttachmentFileForPromotion(String promotionName, String topDealName, UploadPromotionFileRQ info) {
        String promotionSig = topDealBus.getPromotionSig(orgSig, topDealName, PromotionMS.builder().name(promotionName).build());
        info.setPromotionSig(promotionSig);
        topDealBus.uploadPromotionFile(info);
    }

    @And("^As manager, I load below promotions of TopDeal \"([^\"]*)\"$")
    public void managerLoadPromotions(String topDealName, PromotionMS info) {
        topDealBus.loadPromotions(orgSig, topDealName, info);
    }


    @And("^As user, I load TopDeal on org \"([^\"]*)\"$")
    public void userLoadPromotions(String orgName) {
        String orgSig = userSearchBus.loadOrgsByName(orgName).get(0).getSig();
        TopDealMS body = TopDealMS.builder().orgSig(orgSig).build();
        topDealBus.loadOrgTopDeals(body);
    }

    @When("^I remove TopDeal$")
    public void iRemoveTopDeal(List<TopDealMS> list) {
        list.forEach(info ->{
            info.setOrgSig(orgSig);
            info.setTopDealSig(topDealBus.getTopDealSig(info));
            topDealBus.deleteTopDeal(info);
        });
    }


    @When("^I remove promotion of TopDeal \"([^\"]*)\"$")
    public void iRemovePromotion(String topDealName, PromotionMS info) {
        info.setPromotionSig(topDealBus.getPromotionSig(orgSig, topDealName, info));
        topDealBus.deletePromotion(info);
    }




}
