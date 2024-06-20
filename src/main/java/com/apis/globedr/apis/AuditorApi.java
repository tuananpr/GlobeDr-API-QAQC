package com.apis.globedr.apis;

import com.apis.globedr.model.general.PostSig;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.rest.core.response.Response;

public class AuditorApi extends BaseApi {

    private AuditorApi(){}
    private static AuditorApi instant;
    public static AuditorApi getInstant(){
        if(instant == null) instant = new AuditorApi();
        return instant;
    }

    public Response approveQuestion(String postSig) {
        return RestCore.given().url(API.Auditor.APPROVE_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }
    public Response rejectQuestion(String postSig) {
        return RestCore.given().url(API.Auditor.REJECT_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }
}
