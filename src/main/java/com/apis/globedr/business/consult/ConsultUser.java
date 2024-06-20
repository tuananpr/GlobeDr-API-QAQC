package com.apis.globedr.business.consult;

import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.consult.ConsultMS;

import java.util.List;

public class ConsultUser extends ConsultSender {

    public List<QuestionRS> loadQuestions(ConsultMS body) {
       // body.setPostStatus(PostStatus.All.value());
        body.setUserMode(UserType.Patient.value());
        return consultantApi.loadQuestions(body);
    }




}
