package com.apis.globedr.apis;

import com.apis.globedr.enums.PageDashboard;
import com.apis.globedr.model.request.appointment.*;
import com.apis.globedr.model.response.appointment.AppointmentsRS;
import com.apis.globedr.model.response.appointment.ApptDetailsRS;
import com.apis.globedr.business.appointment.BookedApt;
import com.apis.globedr.model.response.appointment.ListOrgExamsRS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.helper.Path;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.AppointmentStatus;
import com.apis.globedr.enums.AppointmentType;
import com.apis.globedr.enums.UserType;
import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.JsonHelper;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AptApi extends BaseApi {

    private AptApi() {
    }

    private static AptApi instant;

    public static AptApi getInstant() {
        if (instant == null) instant = new AptApi();
        return instant;
    }


    public Response uiConfig(Object body) {
        return RestCore.given().url(API.Appt.UI_CONFIG()).auth(token).params(body, UIConfigRQ.class).get().send();
    }


    public Response patient(String apptSig, Map<String, Object> body) {
        body.put(Text.APPT_SIG, apptSig);
        return patient(body);
    }

    public Response patient(Map<String, Object> body) {
        return RestCore.given().url(API.Appt.PATIENT()).auth(token).bodyEncrypt(body).put().send();
    }

    public Response patient(Object body) {
        return RestCore.given().url(API.Appt.PATIENT()).auth(token).bodyEncrypt(body, PatientRQ.class).put().send();
    }


    public Response doctorSchedules(String orgSig, String serviceSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);
        body.put(Text.SERVICES_SIG, serviceSig);
        return doctorSchedules(body);
    }


    public Response doctorSchedules(Map<String, Object> body) {
        body.put(Text.PAGE, null);
        body.put(Text.PAGE_SIZE, null);
        return RestCore.given().url(API.Appt.DOCTOR_SCHEDULES()).auth(token).bodyEncrypt(body).post().send();
    }

    public ApptDetailsRS info(String apptSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.APPT_SIG, apptSig);
        return info(body);
    }


    public ApptDetailsRS info(Object body) {
        return RestCore.given().url(API.Appt.INFO()).auth(token)
                .bodyEncrypt(body, InfoRQ.class).post().send()
                .extractAsModel("data.appt", ApptDetailsRS.class);
    }

    public ApptDetailsRS info(Map<String, Object> body) {
        body.replace(Text.PAGE_DASHBOARD, PageDashboard.value(body.get(Text.PAGE_DASHBOARD)));
        return RestCore.given().url(API.Appt.INFO()).auth(token).bodyEncrypt(body).post().send()
                .extractAsModel("data.appt", ApptDetailsRS.class);
    }

    public ListDoctor loadListDoctor(Object body) {
        return RestCore.given().url(API.Appt.DOCTORS()).auth(token).bodyEncrypt(body, DoctorsRQ.class).post().send()
                .extractAsModel("data", ListDoctor.class);
    }

    public ListDoctor doctors(Object body) {
        return RestCore.given().url(API.Appt.DOCTORS()).auth(token)
                .bodyEncrypt(body, DoctorsRQ.class).post().send()
                .extractAsModel("data", ListDoctor.class);
    }


    public ListDoctor loadListDoctor(String orgSig, String name, String specialtyCode) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);

        if (name != null) {
            body.put(Text.NAME, name);
        }

        if (specialtyCode != null) {
            body.put(Text.SPECIALTY_CODE, specialtyCode);
        }

        return loadListDoctor(body);
    }

    public Response appointment(Map<String, Object> body) {
        return RestCore.given().url(API.Appt.APPOINTMENT()).auth(token).body(body).post().send();
    }

    public Response appointments(Map<String, Object> dataTable) {
        Map<String, Object> body = Common.getMap(dataTable);
        body.replace(Text.USER_MODE, UserType.value(dataTable.get(Text.USER_MODE)));
        body.replace(Text.TYPE, AppointmentType.value(dataTable.get(Text.TYPE)));
        body.replace(Text.STATUS, AppointmentStatus.value(dataTable.get(Text.STATUS)));
        body.put(Text.PAGE, 1);
        body.put(Text.PAGE_SIZE, 20);

        return RestCore.given().url(API.Appt.APPOINTMENTS()).auth(token)
                .bodyEncrypt(body).post().send();
    }

    public List<AppointmentsRS> appointments(Object body) {
        return RestCore.given().url(API.Appt.APPOINTMENTS()).auth(token)
                .bodyEncrypt(body, AppointmentsRQ.class).post().send()
                .extractAsModels("data.list", AppointmentsRS.class);
    }

    public ApptDetailsRS appt(BookedApt bookedApt) {
        return RestCore.given().url(API.Appt.APPT()).auth(token)
                .bodyEncrypt(bookedApt).post().send().extractAsModel("data", ApptDetailsRS.class);
    }


    public ListOrgExamsRS orgExaminationSchedule(OrgExaminationScheduleRQ body) {
        return RestCore.given().url(API.Appt.ORG_EXAMINATION_SCHEDULE())
                .auth(token)
                .bodyEncrypt(body, OrgExaminationScheduleRQ.class).post().send()
                .extractAsModel("data", ListOrgExamsRS.class);
    }


    public Response apptUpdate(Object body) {
        return RestCore.given().url(API.Appt.APPT_UPDATE()).auth(token)
                .bodyEncrypt(body, ApptUpdateRQ.class).post().send();
    }

    public Response infoByQRcode(Object body) {
        return RestCore.given().url(API.Appt.INFO_BY_QRCODE()).bodyEncrypt(body, InfoByQRcodeRQ.class).post().send();
    }

    public Response doctorDecline(Object body) {
        return RestCore.given().url(API.Appt.DOCTOR_DECLINE()).auth(token).bodyEncrypt(body, DoctorDeclineRQ.class).post().send();
    }
}
