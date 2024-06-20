package com.apis.globedr.apis;

import com.apis.globedr.constant.API;
import com.apis.globedr.model.request.telemedicine.TelemedicineBusyRQ;
import com.apis.globedr.model.request.telemedicine.*;
import com.apis.globedr.model.response.telemedicine.TelemedicineCallRS;
import com.apis.globedr.model.response.telemedicine.CallRecipientRS;
import com.apis.globedr.model.response.telemedicine.TelemedicineReceiveRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.List;

public class TelemedicineApi extends BaseApi{


    public Response mode(Object body) {
        return RestCore.given().url(API.Consult.MODE()).auth(token).bodyEncrypt(body, TelemedicineModeRQ.class).put().send();
    }

    public List<CallRecipientRS> doctors(Object body) {
        return RestCore.given().url(API.Consult.DOCTORS()).auth(token)
                .bodyEncrypt(body, TelemedicineDoctorsRQ.class).post().send()
                .extractAsModels("data.other", CallRecipientRS.class);
    }


    public TelemedicineCallRS call(Object body) {
        return RestCore.given().url(API.Chat.CALL()).auth(token)
                .bodyEncrypt(body, TelemedicineCallRQ.class).post().send()
                .extractAsModel("data.info" , TelemedicineCallRS.class);
    }


    public TelemedicineReceiveRS receive(Object body) {
        return RestCore.given().url(API.Chat.RECEIVE()).auth(token).bodyEncrypt(body, TelemedicineReceiveRQ.class).post().send()
                .extractAsModel("data.info", TelemedicineReceiveRS.class);
    }


    public Response busy(Object body) {
        return RestCore.given().url(API.Chat.RECEIVER_BUSY()).auth(token)
                .bodyEncrypt(body, TelemedicineBusyRQ.class).post().send();
    }

    public Response miss(Object body) {
        return RestCore.given().url(API.Chat.MISS()).auth(token).bodyEncrypt(body, TelemedicineMissRQ.class).post().send();
    }


    public Response end(Object body) {
        return RestCore.given().url(API.Chat.END()).auth(token).bodyEncrypt(body, TelemedicineEndRQ.class).post().send();
    }



}
