package com.apis.globedr.apis;

import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.request.topdeal.*;
import com.apis.globedr.model.response.topDeal.LoadOrgTopDealsRS;
import com.apis.globedr.model.response.topDeal.LoadPromotionsRS;
import com.apis.globedr.model.response.topDeal.LoadTopDealsRS;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.model.general.file.File;
import com.rest.core.response.Response;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TopDealApi extends BaseApi {

    private TopDealApi() {
    }

    private static TopDealApi instant;

    public static TopDealApi getInstant() {
        if (instant == null) instant = new TopDealApi();
        return instant;
    }


    public List<LoadTopDealsRS> loadTopDeals(Object body) {
        return RestCore.given().url(API.TopDeal.LOAD_TOP_DEALS()).auth(token)
                .body(body, LoadTopDealsRQ.class).post().send()
                .extractAsModels("data.list", LoadTopDealsRS.class);
    }

    public List<LoadPromotionsRS> loadPromotions(Object body) {
        return RestCore.given().url(API.TopDeal.LOAD_PROMOTIONS()).auth(token)
                .body(body, LoadPromotionsRQ.class).post().send()
                .extractAsModels("data.list", LoadPromotionsRS.class);
    }

    public List<LoadOrgTopDealsRS> loadOrgTopDeals(Object body) {
        return RestCore.given().url(API.TopDeal.LOAD_ORG_TOP_DEALS()).auth(token)
                .body(body, LoadOrgTopDealsRQ.class).post().send()
                .extractAsModels("data.list", LoadOrgTopDealsRS.class);
    }

    public Response promotion(Object body) {
        return RestCore.given().url(API.TopDeal.PROMOTION()).auth(token).body(body, PromotionRQ.class).post().send();
    }

    public Response topDeal(Object body) {
        return RestCore.given().url(API.TopDeal.TOP_DEAL()).auth(token).body(body, TopDealRQ.class).post().send();
    }

    public Response deleteTopDeal(Object body) {
        return RestCore.given().url(API.TopDeal.DELETE_TOP_DEAL()).auth(token).body(body, DeleteTopDealRQ.class).post().send();
    }

    public Response deletePromotion(Object body) {
        return RestCore.given().url(API.TopDeal.DELETE_PROMOTION()).auth(token).body(body, DeletePromotionRQ.class).post().send();
    }


    public Response uploadPromotionIcon(UploadPromotionIconRQ body) {
        return RestCore.given().url(API.TopDeal.UPLOAD_PROMOTION_ICON()).auth(token).multipart(body).post().send();
    }

    public Response uploadPromotionFile(UploadPromotionFileRQ body) {
        return RestCore.given().url(API.TopDeal.UPLOAD_PROMOTION_FILE()).auth(token).multipart(body).post().send();
    }


}
