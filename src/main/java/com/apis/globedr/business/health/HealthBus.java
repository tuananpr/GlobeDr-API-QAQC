package com.apis.globedr.business.health;

import com.apis.globedr.apis.HealthApi;
import com.apis.globedr.enums.DocType;
import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.model.general.VisitSig;
import com.apis.globedr.model.request.appointment.PatientRQ;
import com.apis.globedr.model.request.health.HealthHistoryInfoRQ;
import com.apis.globedr.model.request.health.UpdateMeasurementUnitRQ;
import com.apis.globedr.model.request.health.VisitMedicalHistoryRQ;
import com.apis.globedr.model.request.health.NewVisitDocRQ;
import com.apis.globedr.model.request.health.healthHistory.QuestionItemsRQ;
import com.apis.globedr.model.response.health.*;
import com.apis.globedr.model.response.health.healthHistory.HealthHistoryRS;
import com.apis.globedr.model.response.health.healthHistory.PDF831RS;
import com.apis.globedr.model.response.health.healthHistory.QuestionItemsRS;
import com.apis.globedr.model.step.health.*;
import com.apis.globedr.model.step.health.healthHistory.HealthHistoryInfoMS;
import com.apis.globedr.model.step.health.healthHistory.HealthHistoryMS;
import com.apis.globedr.model.step.org.AfterVisitMS;
import com.google.gson.JsonObject;
import com.rest.core.response.Response;

import java.util.List;
import java.util.stream.Collectors;

public class HealthBus {
    HealthApi healthApi = HealthApi.getInstant();


    public List<String> getHealthDocSigs(HealthDocMS body) {
        return loadHealthDocs(body).stream().map(d -> d.getDocSig()).collect(Collectors.toList());
    }

    public List<LoadHealthDocsRS> loadHealthDocs(HealthDocMS body) {
        if (body.getMedicalType().equals(MedicalType.Insurance.value()) || body.getMedicalType().equals(MedicalType.IdCard.value())) {
            return healthApi.loadHealthDocs(body)
                    .extractAsModels("data.list", LoadInsuranceDocsRS.class)
                    .stream()
                    .flatMap(body.getDescription() != null ?
                            docs -> docs.getHealthDocs().stream()
                                    .filter(d -> d.getDescription() != null && d.getDescription().equalsIgnoreCase(body.getDescription())) :
                            docs -> docs.getHealthDocs().stream()
                    )
                    .collect(Collectors.toList());
        } else {
            return healthApi.loadHealthDocs(body).extractAsModels("data.list", LoadHealthDocsRS.class)
                    .stream().filter(body.getDescription() != null ?
                            doc -> doc.getDescription().equalsIgnoreCase(body.getDescription()) : doc -> true
                    ).collect(Collectors.toList());
        }
    }


    public Response newVisitData(AfterVisitMS body) {
        return healthApi.newVisitData(body);
    }

    public Response updateVisitData(AfterVisitMS body) {
        return healthApi.updateVisitData(body);
    }


    public List<VisitDataRS> visitData(AfterVisitMS body) {
        return healthApi.visitData(body);
    }

    public List<VisitDataRS> visitData(String orgSig, String userSig) {
        return visitData(AfterVisitMS.builder().orgSig(orgSig).userSig(userSig).build());
    }

    public Response uploadDoc(NewVisitDocRQ body) {
        return healthApi.uploadDoc(body);
    }

    public PatientRQ adDocsToVerifyAptInfo(String aptSig, String userSig, List<HealthDocMS> list) {
        PatientRQ body = PatientRQ.builder().build();
        body.setApptSig(aptSig);

        List<AddHealthDocRS> docs = list.stream().map(info -> {
            info.setApptSig(aptSig);
            info.setUserSig(userSig);
            return addHealthDocs(info);
        }).collect(Collectors.toList());

        docs.forEach(info -> {
            if (info.getHealthDoc().getDocType().equals(DocType.PassPort.value()) && info.getHealthDoc().getAttributes().equals(1)) {
                body.setIdCardDocIdFront(1);
                body.setIdCardDocSigFront(info.getHealthDoc().getDocSig());
            }

            if (info.getHealthDoc().getDocType().equals(DocType.PassPort.value()) && info.getHealthDoc().getAttributes().equals(2)) {
                body.setIdCardDocIdBack(2);
                body.setIdCardDocSigBack(info.getHealthDoc().getDocSig());
            }

            if (info.getHealthDoc().getDocType().equals(DocType.MedicalSupplement1.value()) && info.getHealthDoc().getAttributes().equals(1)) {
                body.setInsuDocIdFront(3);
                body.setInsuDocSigFront(info.getHealthDoc().getDocSig());
            }

            if (info.getHealthDoc().getDocType().equals(DocType.MedicalSupplement1.value()) && info.getHealthDoc().getAttributes().equals(2)) {
                body.setInsuDocIdBack(4);
                body.setInsuDocSigBack(info.getHealthDoc().getDocSig());
            }
        });
        return body;
    }

    public AddHealthDocRS addHealthDocs(HealthDocMS body) {
        return healthApi.addHealthDocs(body);
    }

    public Response moveHealthDocs(HealthDocMS body) {
        return healthApi.moveHealthDocs(body);
    }


    public Response updateDescription(HealthDocMS body) {
        return healthApi.updateDescription(body);
    }

    public Response removeHealthDocs(HealthDocMS body) {
        return healthApi.removeHealthDocs(body);
    }


    public Response loadVisitMedical(String orgSig, String userSig) {
        String visitSig = visitData(orgSig, userSig).get(0).getVisitSig();
        return loadVisitMedical(visitSig);
    }


    public Response loadVisitMedical(String visitSig) {
        return healthApi.loadVisitMedical(new VisitSig(visitSig));
    }

    public List<VisitMedicalHistoryRS> loadVisitMedicalHistory(String userSig) {
        VisitMedicalHistoryRQ body = VisitMedicalHistoryRQ.builder().userSig(userSig).build();
        return healthApi.loadVisitMedicalHistory(body);
    }


    public Response updateMeasurementUnit(UpdateMeasurementUnitRQ body) {
        return healthApi.updateMeasurementUnit(body);
    }

    public Response saveBloodType(HealthMS body) {
        return healthApi.saveBloodType(body);
    }


    public Response addBMI(BmiMS body) {
        return healthApi.addBMI(body);
    }

    public Response deleteHealthData(BmiMS body) {
        return healthApi.deleteHealthData(body);
    }

    public List<LoadDataBMIRS> loadDataBmi(BmiMS body) {
        return healthApi.loadDataBmi(body);
    }

    public List<String> getBmiSigs(BmiMS body) {
        return loadDataBmi(body).stream().map(d -> d.getSig()).collect(Collectors.toList());
    }

    public String getBmiSig(BmiMS body) {
        return getBmiSigs(body).get(0);
    }

    public Response loadBMIChart(BmiMS body) {
        return healthApi.loadBMIChart(body);
    }

    public Response loadLastBMI(BmiMS body) {
        return healthApi.loadLastBMI(body);
    }

    public Response updateGrowthTarget(GrowthTargetMS body) {
        return healthApi.updateGrowthTarget(body);
    }

    public Response loadGrowthTarget(GrowthTargetMS body) {
        return healthApi.loadGrowthTarget(body);
    }

    public Response loadGrowthTargetFull(GrowthTargetMS body) {
        return healthApi.loadGrowthTargetFull(body);
    }

    public Response loadGrowthChart(GrowthChartMS body) {
        return healthApi.loadGrowthChart(body);
    }

    public Response addBloodGlucose(BloodGlucoseMS body) {
        return healthApi.addBloodGlucose(body);
    }

    public Response loadBloodGlucose(BloodGlucoseMS body) {
        return healthApi.loadBloodGlucose(body);
    }

    public Response loadBloodGlucoseChart(BloodGlucoseMS body) {
        return healthApi.loadBloodGlucoseChart(body);
    }

    public Response loadStatus(HealthMS body) {
        return healthApi.loadStatus(body);
    }

    public Response addBloodPressure(BloodPressureMS body) {
        return healthApi.addBloodPressure(body);
    }

    public Response loadBloodPressure(BloodPressureMS body) {
        return healthApi.loadBloodPressure(body);
    }

    public Response loadBloodPressureChart(BloodPressureMS body) {
        return healthApi.loadBloodPressureChart(body);
    }

    public Response loadLastVitals(BloodPressureMS body) {
        return healthApi.loadLastVitals(body);
    }

    public List<HealthHistoryRS> healthHistory(HealthHistoryMS body) {
        return healthApi.healthHistory(body);
    }

    public List<HealthHistoryRS> healthHistory(HealthHistoryInfoMS body) {
        return healthApi.healthHistory(body);
    }

    public QuestionItemsRS getQuestionItem(HealthHistoryInfoMS body) {
        return healthHistory(body).stream()
                .flatMap(rs -> rs.getQuestions().stream().filter(questions -> questions.getName().equalsIgnoreCase(body.getQuestionName())))
                .flatMap(question -> question.getQuestionItems().stream().filter(item -> item.getItemName().equalsIgnoreCase(body.getItemName())))
                .findFirst().orElse(null);

    }

    public PDF831RS printHealthHistory(String userSig) {
        HealthHistoryMS body = HealthHistoryMS.builder().userSig(userSig).build();
        return healthApi.printHealthHistory(body);
    }

    public PDF831RS printHealthHistory(HealthHistoryMS body) {
        return healthApi.printHealthHistory(body);
    }

    public Response healthHistoryInfo(String userSig, List<HealthHistoryInfoMS> list) {
        list.forEach(info ->{
            info.setUserSig(userSig);
            info.setQuestionItemSig(getQuestionItem(info).getQuestionItemSig());
        });

        List<QuestionItemsRQ> questions = list.stream().map(info -> QuestionItemsRQ.builder()
                .answerData(info.getAnswerData())
                .itemType(info.getItemType())
                .questionItemSig(info.getQuestionItemSig()).build()
        ).collect(Collectors.toList());

        HealthHistoryInfoRQ body = HealthHistoryInfoRQ.builder()
                .userSig(userSig)
                .questionItemInfos(questions)
                .build();

        return healthHistoryInfo(body);
    }



    public Response healthHistoryInfo(HealthHistoryInfoRQ body) {
        return healthApi.healthHistoryInfo(body);
    }

    public Response healthDashboard(HealthMS body) {
        return healthApi.healthDashboard(body);
    }


}
