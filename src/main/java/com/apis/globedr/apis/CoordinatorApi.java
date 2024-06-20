package com.apis.globedr.apis;

import com.apis.globedr.model.general.PostSig;
import com.apis.globedr.model.request.consult.*;
import com.apis.globedr.model.response.consult.LoadDoctorAssignRS;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.rest.core.response.Response;

import java.util.List;

public class CoordinatorApi extends BaseApi {


    private CoordinatorApi() {
    }

    private static CoordinatorApi instant;

    public static CoordinatorApi getInstant() {
        if (instant == null) instant = new CoordinatorApi();
        return instant;
    }

    public Response closeQuestion(Object body) {
        return RestCore.given().url(API.Coordinator.CLOSE_QUESTION()).auth(token).bodyEncrypt(body, CloseQuestionRQ.class).put().send();

    }

    public Response SpamQuestion(Object body) {
        return RestCore.given().url(API.Coordinator.SPAM_QUESTION()).auth(token).bodyEncrypt(body, SpamQuestionRQ.class).put().send();
    }

    public Response getActivityLog(String postSig) {
        return RestCore.given().url(API.Coordinator.GET_ACTIVITY_LOG()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }


    public List<LoadDoctorAssignRS> loadDoctorAssign(Object body) {
        return RestCore.given().url(API.Coordinator.LOAD_DOCTOR_ASSIGN()).auth(token)
                .bodyEncrypt(body, LoadDoctorAssignRQ.class).post().send()
                .extractAsModels("data.list", LoadDoctorAssignRS.class);
    }


    public List<LoadDoctorAssignRS> loadAuditAssign(Object body) {
        return RestCore.given().url(API.Coordinator.LOAD_DOCTOR_AUDIT()).auth(token)
                .bodyEncrypt(body, LoadAuditorAssignRQ.class).post().send()
                .extractAsModels("data.list", LoadDoctorAssignRS.class);

    }

    public Response assignQuestion(Object body) {
        return RestCore.given().url(API.Coordinator.ASSIGN_QUESTION()).auth(token)
                .bodyEncrypt(body, AssignQuestionRQ.class).post().send();
    }

    public Response submitQuestion(Object body) {
        return RestCore.given().url(API.Coordinator.SUBMIT_AUDIT()).auth(token)
                .bodyEncrypt(body, AssignQuestionRQ.class).post().send();
    }


    public Response sendDoctorNoti(String postSig) {
        return RestCore.given().url(API.Coordinator.SEND_DOCTOR_NOTI()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }

    public Response sendPatientNoti(String postSig) {
        return RestCore.given().url(API.Coordinator.SEND_PATIENT_NOTI()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }


    public Response sendAuditorNoti(String postSig) {
        return RestCore.given().url(API.Coordinator.SEND_AUDITOR_NOTI()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send();
    }

    public Response exportQuestionStatistic(Object body) {
        return RestCore.given().url(API.Coordinator.EXPORT_QUESTION_STATICTIS()).auth(token)
                .bodyEncrypt(body, LoadQuestionStatisticRQ.class).post().send();
    }

    public Response loadQuestionStatistic(Object body) {

        return RestCore.given().url(API.Coordinator.LOAD_QUESTION_STATICTIS()).auth(token)
                .bodyEncrypt(body, LoadQuestionStatisticRQ.class).post().send();
    }


}
