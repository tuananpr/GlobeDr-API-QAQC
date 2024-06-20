package com.apis.globedr.business.consult;

import com.apis.globedr.model.step.consult.ConsultMS;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.rest.core.response.Response;

public abstract class ConsultSender extends ConsultBus{
    @Override
    public Response sendNotiToPatient(ConsultMS body) {
        return null;
    }

    @Override
    public Response getActivityLog(ConsultMS body) {
        return null;
    }


}
