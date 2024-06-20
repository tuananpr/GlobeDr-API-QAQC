package com.apis.globedr.business.topdeal;

import com.apis.globedr.apis.TopDealApi;
import com.apis.globedr.model.request.topdeal.UploadPromotionFileRQ;
import com.apis.globedr.model.request.topdeal.UploadPromotionIconRQ;
import com.apis.globedr.model.response.topDeal.LoadOrgTopDealsRS;
import com.apis.globedr.model.response.topDeal.LoadPromotionsRS;
import com.apis.globedr.model.response.topDeal.LoadTopDealsRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.topdeal.PromotionMS;
import com.apis.globedr.model.step.topdeal.TopDealMS;
import com.rest.core.response.Response;

import java.util.List;

public class TopDealBus {
    TopDealApi topDealApi = TopDealApi.getInstant();

    public Response addTopDeal(TopDealMS body){
        return topDealApi.topDeal(body);
    }
    public List<LoadTopDealsRS> loadTopDeals(TopDealMS body){
        return topDealApi.loadTopDeals(body);
    }

    public String getTopDealSig(TopDealMS body){
        return loadTopDeals(body).stream().map(t->t.getTopDealSig()).findFirst().orElse(null);
    }

    public List<LoadTopDealsRS> loadTopDeals(String name, String orgSig){
        TopDealMS body = TopDealMS.builder()
                .name(name)
                .orgSig(orgSig)
                .build();
        return topDealApi.loadTopDeals(body);
    }

    public Response addPromotion(PromotionMS body){
        return topDealApi.promotion(body);
    }

    public Response uploadPromotionIcon(UploadPromotionIconRQ body){
        return topDealApi.uploadPromotionIcon(body);
    }

    public Response uploadPromotionFile(UploadPromotionFileRQ body){
        return topDealApi.uploadPromotionFile(body);
    }


    public Response deleteTopDeal(TopDealMS body){
        return topDealApi.deleteTopDeal(body);
    }

    public Response deletePromotion(PromotionMS body){
        return topDealApi.deletePromotion(body);
    }

    public List<LoadPromotionsRS> loadPromotions(PromotionMS body){
        return topDealApi.loadPromotions(body);
    }

    public List<LoadPromotionsRS> loadPromotions(String orgSig, String topDealName, PromotionMS body){
        TopDealMS topDeal = TopDealMS.builder().orgSig(orgSig).name(topDealName).build();
        body.setTopDealSig(getTopDealSig(topDeal));
        return loadPromotions(body);
    }

    public String getPromotionSig(String orgSig, String topDealName, PromotionMS body){
        return loadPromotions(orgSig, topDealName, body).stream().map(p->p.getPromotionSig()).findFirst().orElse(null);
    }


    public List<LoadOrgTopDealsRS> loadOrgTopDeals(TopDealMS body){
        return topDealApi.loadOrgTopDeals(body);
    }
}
