package com.apis.globedr.apis;

import com.apis.globedr.model.general.UserSig;
import com.apis.globedr.model.general.VisitSig;
import com.apis.globedr.model.request.health.*;
import com.apis.globedr.model.response.health.*;
import com.apis.globedr.model.response.health.healthHistory.HealthHistoryRS;
import com.apis.globedr.model.response.health.healthHistory.PDF831RS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;

public class HealthApi extends BaseApi {


    private HealthApi() {
    }

    private static HealthApi instant;

    public static HealthApi getInstant() {
        if (instant == null) instant = new HealthApi();
        return instant;
    }


    public Response moveHealthDocs(Object body) {
        return RestCore.given().url(API.Health.MOVE_HEALTH_DOCS()).auth(token).bodyEncrypt(body, MoveHealthDocsRQ.class).put().send();
    }

    public Response removeHealthDocs(Object body) {
        return RestCore.given().url(API.Health.REMOVE_HEALTH_DOCS()).auth(token).bodyEncrypt(body, RemoveHealthDocsRQ.class).delete().send();
    }

    public Response loadHealthDocs(Object body) {
        return RestCore.given().url(API.Health.LOAD_HEALTH_DOCS()).auth(token).bodyEncrypt(body, LoadHealthDocsRQ.class)
                .post().send();
    }


    public AddHealthDocRS addHealthDocs(Object body) {
        return RestCore.given().url(API.Health.ADD_HEALTH_DOCS()).auth(token).multipart(body, AddHealthDocsRQ.class)
                .post().send().extractAsModel("data", AddHealthDocRS.class);
    }

    public Response updateMeasurementUnit(Object body) {
        return RestCore.given().url(API.Health.UPDATE_MEASUREMENT_UNIT()).auth(token).bodyEncrypt(body, UpdateMeasurementUnitRQ.class).put().send();
    }


    public Response loadGrowthTarget(Object body) {
        return RestCore.given().url(API.Health.LOAD_GROWTH_TARGET()).auth(token).bodyEncrypt(body, LoadGrowthTargetRQ.class).post().send();
    }

    public Response loadGrowthTargetFull(Object body) {
        return RestCore.given().url(API.Health.LOAD_GROWTH_TARGET_FULL()).auth(token).bodyEncrypt(body, LoadGrowthTargetFullRQ.class).post().send();
    }

    public Response loadGrowthChart(Object body) {
        return RestCore.given().url(API.Health.LOAD_GROWTH_CHART()).auth(token).bodyEncrypt(body, LoadGrowthChartRQ.class).post().send();
    }

    public Response loadLastBMI(Object body) {
        return RestCore.given().url(API.Health.LOAD_LAST_BMI()).auth(token).bodyEncrypt(body, LoadLastBMIRQ.class).post().send();
    }


    public Response loadLastVitals(Object body) {
        return RestCore.given().url(API.Health.LOAD_LAST_VITALS()).auth(token).bodyEncrypt(body).post().send();
    }


    public Response loadBMIChart(Object body) {
        return RestCore.given().url(API.Health.LOAD_CHART_BMI()).auth(token).bodyEncrypt(body, LoadBMIChartRQ.class).post().send();
    }


    public Response addBMI(Object body) {
        return RestCore.given().url(API.Health.ADD_BMI()).auth(token).bodyEncrypt(body, AddBmiRQ.class).post().send();
    }

    public Response healthDashboard(Object body) {
        return RestCore.given().url(API.Health.HEALTH_DASHBOARD()).auth(token).params(body, HealthDashboardRQ.class).get().send();
    }

    public List<VisitMedicalHistoryRS> loadVisitMedicalHistory(Object body) {
        return RestCore.given().url(API.Health.VISIT_MEDICAL_HISTORY()).auth(token)
                .bodyEncrypt(body, VisitMedicalHistoryRQ.class).post().send()
                .extractAsModels("data.list", VisitMedicalHistoryRS.class );
    }


    public Response loadVisitMedical(Object body) {
        return RestCore.given().url(API.Health.VISIT_MEDICAL_DATA()).auth(token).params(body, VisitSig.class).get().send();
    }


    public Response newVisitData(Object body) {
        return RestCore.given().url(API.Health.NEW_VISIT_DATA()).auth(token)
                .bodyEncrypt(body, NewVisitDataRQ.class).post().send();
    }

    public Response updateVisitData(Object body) {
        return RestCore.given().url(API.Health.VISIT_DATA()).auth(token)
                .bodyEncrypt(body, NewVisitDataRQ.class).post().send();
    }


    public List<VisitDataRS> visitData(Object body) {
        return RestCore.given().url(API.Health.VISIT_DATAS()).auth(token).bodyEncrypt(body, VisitDataRQ.class)
                .post().send().extractAsModels("data.list", VisitDataRS.class );
    }


    public Response uploadDoc(NewVisitDocRQ body) {
        return RestCore.given().url(API.Health.NEW_VISIT_DOC()).auth(token).multipart(body).post().send();
    }

    public PDF831RS printHealthHistory(Object body) {
        return RestCore.given().url(API.Health.PDF831()).auth(token).params(body, UserSig.class).get().send()
                .extractAsModel("data", PDF831RS.class);
    }

    public Response loadStatus(Object body) {
        return RestCore.given().url(API.Health.LOAD_STATUS()).auth(token).bodyEncrypt(body, LoadStatusRQ.class).post().send();
    }


    public Response loadBloodGlucose(Object body) {
        return RestCore.given().url(API.Health.LOAD_BLOOD_GLUCOSE()).auth(token).bodyEncrypt(body, LoadBloodGlucoseRQ.class).post().send();
    }

    public Response loadBloodGlucoseChart(Object body) {
        return RestCore.given().url(API.Health.LOAD_CHART_BLOOD_GLUCOSE()).auth(token).bodyEncrypt(body, UserSig.class).post().send();
    }


    public List<LoadDataBMIRS> loadDataBmi(Object body) {
        return RestCore.given().url(API.Health.LOAD_DATA_BMI()).auth(token).bodyEncrypt(body, LoadDataBmiRQ.class)
                .post().send().extractAsModels("data.list", LoadDataBMIRS.class );
    }

    public Response addBloodPressure(Object body) {
        return RestCore.given().url(API.Health.ADD_BLOOD_PRESSURE()).auth(token).bodyEncrypt(body, AddBloodPressureRQ.class).post().send();
    }

    public Response addBloodGlucose(Object body) {
        return RestCore.given().url(API.Health.ADD_BLOOD_GLUCOSE()).auth(token).bodyEncrypt(body, AddBloodGlucoseRQ.class).post().send();
    }


    public Response loadBloodPressure(Object body) {
        return RestCore.given().url(API.Health.LOAD_BLOOD_PRESSURE()).auth(token).bodyEncrypt(body, LoadBloodPressureRQ.class).post().send();
    }

    public Response loadBloodPressureChart(Object body) {
        return RestCore.given().url(API.Health.LOAD_CHART_BLOOD_PRESSURE()).auth(token).bodyEncrypt(body, LoadChartBloodPressureRQ.class).post().send();
    }

    public Response updateGrowthTarget(Object body) {
        return RestCore.given().url(API.Health.UPDATE_GROWTH_TARGET()).auth(token).bodyEncrypt(body, UpdateGrowthTargetRQ.class).post().send();
    }


    public Response saveBloodType(Object body) {
        return RestCore.given().url(API.Health.SAVE_BLOOD_TYPE()).auth(token).bodyEncrypt(body, SaveBloodTypeRQ.class).put().send();
    }

    public Response healthHistoryInfo(JsonObject json) {
        return RestCore.given().url(API.Health.HEALTH_HISTORY_INFO()).auth(token).bodyEncrypt(json).post().send();
    }

    public Response healthHistoryInfo(Object body) {
        return RestCore.given().url(API.Health.HEALTH_HISTORY_INFO()).auth(token).bodyEncrypt(body, HealthHistoryInfoRQ.class).post().send();
    }

    public List<HealthHistoryRS> healthHistory(Object body) {
        return RestCore.given().url(API.Health.HEALTH_HISTORY()).auth(token)
                .bodyEncrypt(body, HealthHistoryRQ.class).post().send()
                .extractAsModels("data.list" , HealthHistoryRS.class);
    }

    public Response updateDescription(Object body) {
        return RestCore.given().url(API.Health.UPDATE_DESCRIPTION()).auth(token).bodyEncrypt(body, UpdateDescriptionRQ.class).put().send();
    }


    public Response deleteHealthData(Object body) {
        return RestCore.given().url(API.Health.DELETE_HEALTH_DATA()).auth(token).bodyEncrypt(body, DeleteHealthDataRQ.class).delete().send();
    }

    public Response prescriptions(Map<String, Object> body) {
        return RestCore.given().url(API.Health.PRESCRIPTIONS()).auth(token).bodyEncrypt(body).post().send();
    }


}

