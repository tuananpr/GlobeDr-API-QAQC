package com.apis.globedr.business.consult;

import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.rest.core.response.Response;

public abstract class ConsultRecipient extends ConsultBus {


    public void acceptQuestion(ConsultMS body) {
        loadQuestionsByContent(body).forEach(info -> {
            providerApi.acceptQuestion(info.getPostSig());
        });

    }

    public void giveBackQuestion(ConsultMS body) {
        loadQuestionsByContent(body).forEach(info -> {
            body.setPostSig(info.getPostSig());
            providerApi.giveBackQuestion(body);
        });
    }

    public void declineQuestion(ConsultMS body) {
        loadQuestionsByContent(body).forEach(info -> {
            providerApi.declineQuestion(info.getPostSig());
        });
    }

    public void completeQuestion(ConsultMS body) {
        loadQuestionsByContent(body).forEach(info -> {
            providerApi.completeQuestion(info.getPostSig());
        });
    }

    public Response rejectQuestion(ConsultMS consult) {
        QuestionRS post = loadQuestionsByContent(consult).stream().findFirst().orElse(null);
        return auditorApi.rejectQuestion(post.getPostSig());
    }

    public Response approveQuestion(ConsultMS consult) {
        QuestionRS post = loadQuestionsByContent(consult).stream().findFirst().orElse(null);
        return auditorApi.approveQuestion(post.getPostSig());
    }

    public Response sendNotiToPatient(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.sendPatientNoti(postSig);
    }

    public Response getActivityLog(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return coordinatorApi.getActivityLog(postSig);
    }



}



