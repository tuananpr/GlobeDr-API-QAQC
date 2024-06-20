package com.apis.globedr.business.consult;

import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.request.consult.AssignQuestionRQ;
import com.apis.globedr.model.request.consult.LoadDoctorAssignRQ;
import com.apis.globedr.model.request.consult.LoadQuestionStatisticRQ;
import com.apis.globedr.model.response.consult.LoadDoctorAssignRS;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.rest.core.response.Response;

import java.util.List;

public abstract class ConsultManager extends ConsultBus {

    public List<QuestionRS> loadQuestions(ConsultMS body) {
        body.setUserMode(UserType.Coordinator.value());
        if (body.getDoctorName() != null) {
            String sig = coordinatorApi.loadDoctorAssign(body).stream().map(d -> d.getSignature()).findFirst().orElse(null);
            body.setProviderSig(sig);
        }

        if (body.getAuditorName() != null) {
            String sig = coordinatorApi.loadAuditAssign(body).stream().map(d -> d.getSignature()).findFirst().orElse(null);
            body.setAuditorSig(sig);
        }
        return consultantApi.loadQuestions(body);
    }


    public Response assignQuestion(String doctorName, ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        LoadDoctorAssignRQ rLoadDoctor = LoadDoctorAssignRQ
                .builder()
                .postSig(postSig)
                .name(doctorName)
                .build();
        LoadDoctorAssignRS doctor = coordinatorApi.loadDoctorAssign(rLoadDoctor).stream().findFirst().orElse(null);

        assert doctor != null;
        AssignQuestionRQ rAssign = AssignQuestionRQ
                .builder()
                .postSig(postSig)
                .userSig(doctor.getSignature())
                .build();
        return coordinatorApi.assignQuestion(rAssign);
    }


    public Response submitQuestion(String auditorName, ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        LoadDoctorAssignRQ rLoadDoctor = LoadDoctorAssignRQ
                .builder()
                .postSig(postSig)
                .name(auditorName)
                .build();
        LoadDoctorAssignRS doctor = coordinatorApi.loadAuditAssign(rLoadDoctor).stream().findFirst().orElse(null);

        AssignQuestionRQ rAssign = AssignQuestionRQ
                .builder()
                .postSig(postSig)
                .userSig(doctor.getSignature())
                .build();
        return coordinatorApi.submitQuestion(rAssign);
    }

    public Response closeQuestion(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        body.setPostSig(postSig);
        return coordinatorApi.closeQuestion(body);
    }


    public Response spamQuestion(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        body.setPostSig(postSig);
        return coordinatorApi.SpamQuestion(body);
    }

    public Response sendNotiToDoctor(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.sendDoctorNoti(postSig);
    }

    public Response sendNotiToPatient(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.sendPatientNoti(postSig);
    }

    public Response sendNotiToAuditor(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.sendAuditorNoti(postSig);
    }



    public Response loadQuestionStatistic(LoadQuestionStatisticRQ body) {
        return coordinatorApi.loadQuestionStatistic(body);
    }


    public Response exportQuestionStatistic(LoadQuestionStatisticRQ body) {
        return coordinatorApi.exportQuestionStatistic(body);
    }



    public Response questionReviews() {
        return consultantApi.questionReviews();
    }

    public Response getActivityLog(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.getActivityLog(postSig);
    }

}
