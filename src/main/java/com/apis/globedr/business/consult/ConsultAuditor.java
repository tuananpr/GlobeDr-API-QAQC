package com.apis.globedr.business.consult;

import com.apis.globedr.apis.ConsultantApi;
import com.apis.globedr.enums.UserType;
import com.apis.globedr.model.response.consult.QuestionRS;
import com.apis.globedr.model.step.consult.ConsultMS;

import java.util.List;

public class ConsultAuditor extends ConsultRecipient {
    @Override
    public List<QuestionRS> loadQuestions(ConsultMS body) {
        body.setUserMode(UserType.Auditor.value());
        return ConsultantApi.getInstant().loadQuestions(body);
    }


}
