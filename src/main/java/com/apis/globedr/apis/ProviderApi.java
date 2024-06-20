package com.apis.globedr.apis;

import com.apis.globedr.model.general.PostSig;
import com.apis.globedr.model.request.consult.GiveBackQuestionRQ;
import com.apis.globedr.model.request.provider.GiftPointRQ;
import com.apis.globedr.model.request.provider.LogReceiverFeesRQ;
import com.apis.globedr.model.request.provider.SearchUserRQ;
import com.apis.globedr.model.request.provider.SearchUserRS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.rest.core.response.Response;

import java.util.List;

public class ProviderApi extends BaseApi {

    private ProviderApi(){}
    private static ProviderApi instant;
    public static ProviderApi getInstant(){
        if(instant == null) instant = new ProviderApi();
        return instant;
    }

    public void completeQuestion(String postSig) {

        RestCore.given().url(API.Provider.COMPLETE_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }


    public void declineQuestion(String postSig) {
        RestCore.given().url(API.Provider.DECLINE_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }


    public Response acceptQuestion(String postSig) {
        return RestCore.given().url(API.Provider.ACCEPT_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }

    public Response giveBackQuestion(ConsultMS body) {
        return RestCore.given().url(API.Provider.GIVE_BACK_QUESTION()).auth(token).bodyEncrypt(body, GiveBackQuestionRQ.class).post().send();
    }


    public List<SearchUserRS> searchUser(Object body) {
        return RestCore.given().url(API.Provider.SEARCH_USER()).auth(token).bodyEncrypt(body, SearchUserRQ.class).post().send()
                .extractAsModels("data.list", SearchUserRS.class);
    }


    public Response giftPoint(Object body) {
        return RestCore.given().url(API.Provider.GIFT_POINT()).auth(token).bodyEncrypt(body, GiftPointRQ.class).post().send();
    }

    public Response reportOrgs() {
        return RestCore.given().url(API.Provider.REPORT_ORGS()).auth(token).get().send();
    }

    public Response reportFees(Object body) {
        return RestCore.given().url(API.Provider.REPORT_FEES()).auth(token).bodyEncrypt(body, LogReceiverFeesRQ.class).post().send();
    }

    public Response logReceiverFees(Object body) {
        return RestCore.given().url(API.Provider.LOG_RECEIVER_FEES()).auth(token).bodyEncrypt(body, LogReceiverFeesRQ.class).post().send();
    }
}
