package com.apis.globedr.business.appointment;

import com.apis.globedr.apis.AptManagerApi;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.appointment.*;
import com.apis.globedr.model.response.appointment.ApptDetailsRS;
import com.apis.globedr.model.response.appointment.ListExamsSchedule;
import com.apis.globedr.model.step.appointment.ApptMS;
import com.rest.core.response.Response;

import java.util.List;

public class AptManageBus {
    private AptManagerApi aptManager = AptManagerApi.getInstant();
    public ExamsSchedule scheduleExamination(ExamsSchedule body){
        return aptManager.scheduleExamination(body);
    }

    public Response newDoctorSchedule(NewOrgDoctorScheduleRQ body){
        return aptManager.newDoctorSchedule(body);
    }

    public String getAptSig(String orgSig, String patientName){
        ApptMS body = ApptMS.builder().orgSig(orgSig).patientName(patientName).build();
        return getAptSig(body);
    }

    public String getAptSig(ApptMS body){
        return appts(body).stream().map(a -> a.getApptSig()).findFirst().orElse(null);
    }

    public List<ApptDetailsRS> appts(ApptMS body){
        return aptManager.appts(body);
    }

    public List<ApptDetailsRS> appts(String orgSig){
        return aptManager.appts(ApptMS.builder().orgSig(orgSig).build());
    }

    public Response status(ApptMS body){
        return aptManager.status(body);
    }

    public ListExamsSchedule orgScheduleExaminations(String orgSig){
        return aptManager.orgScheduleExaminations(new OrgSig(orgSig));
    }

    public Response apptAssignDoctor(ApptMS body){
        return aptManager.apptAssignDoctor(body);
    }
    public Response doctorScheduleVisitViews(String userSig){
        DoctorScheduleVisitViewsRQ body = DoctorScheduleVisitViewsRQ.builder().userSig(userSig).build();
        return aptManager.doctorScheduleVisitViews(body);
    }

    public Response doctorScheduleVisit(DoctorScheduleVisitRQ body){
        return aptManager.doctorScheduleVisit(body);
    }

    public Response appointmentConfig(ApptMS body){
        return aptManager.appointmentConfig(body);
    }

}

