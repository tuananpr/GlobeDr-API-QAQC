package com.apis.globedr.apis;

import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.request.appointment.*;
import com.apis.globedr.model.response.appointment.ApptDetailsRS;
import com.apis.globedr.model.response.appointment.ListExamsSchedule;
import com.rest.core.RestCore;
import com.google.gson.JsonObject;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.JsonHelper;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AptManagerApi extends BaseApi {


    private AptManagerApi() {
    }

    private static AptManagerApi instant;

    public static AptManagerApi getInstant() {
        if (instant == null) instant = new AptManagerApi();
        return instant;
    }


    public Response newDoctorSchedule(NewOrgDoctorScheduleRQ body) {
        return RestCore.given().url(API.ApptManage.NEW_ORG_DOCTOR_SCHEDULE()).auth(token).bodyEncrypt(body, NewOrgDoctorScheduleRQ.class).post().send();
    }

    public ListExamsSchedule orgScheduleExaminations(Object body) {
        return RestCore.given().url(API.ApptManage.ORG_SCHEDULE_EXAMINATIONS()).auth(token)
                .bodyEncrypt(body, OrgSig.class).post().send()
                .extractAsModel("data", ListExamsSchedule.class);
    }

    public ExamsSchedule scheduleExamination(Object body) {
        return RestCore.given().url(API.ApptManage.SCHEDULE_EXAMINATION()).auth(token)
                .bodyEncrypt(body, ExamsSchedule.class).post().send()
                .extractAsModel("data.info", ExamsSchedule.class);
    }



    public List<ApptDetailsRS> appts(Object body) {
        return RestCore.given().url(API.ApptManage.APPTS()).auth(token).bodyEncrypt(body, ApptsRQ.class)
                .post().send()
                .extractAsModels("data.list", ApptDetailsRS.class);
    }


    public Response status(String orgSig, String aptSig, String appointmentStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);
        body.put(Text.APPT_SIG, aptSig);
        body.put(Text.STATUS, AppointmentStatus.value(appointmentStatus));
        return status(body);
    }

    public Response status(Object body) {
        return RestCore.given().url(API.ApptManage.STATUS()).auth(token).bodyEncrypt(body , StatusRQ.class).put().send();
    }


    public Response doctorScheduleVisitViews(Object body) {
        return RestCore.given().url(API.ApptManage.DOCTOR_SCHEDULE_VISIT_VIEWS()).auth(token).bodyEncrypt(body, DoctorScheduleVisitViewsRQ.class).post().send();
    }


    public Response doctorScheduleVisit(DoctorScheduleVisitRQ body) {
        return RestCore.given().url(API.ApptManage.DOCTOR_SCHEDULE_VISIT()).auth(token).bodyEncrypt(body).post().send();
    }


    public Response appointmentConfig(Object body) {
        return RestCore.given().url(API.ApptManage.APPOINTMENT_CONFIG()).auth(token)
                .bodyEncrypt(body, AppointmentConfigRQ.class).put().send();
    }



    public Response apptAssignDoctor(Object body) {
        return RestCore.given().url(API.ApptManage.APPT_ASSIGN_DOCTOR()).auth(token).bodyEncrypt(body, ApptAssignDoctorRQ.class).put().send();
    }



}
