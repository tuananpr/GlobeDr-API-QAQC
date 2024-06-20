package stepdefinition.healthStep;

import com.apis.globedr.business.appointment.AptBus;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.orgMember.OrgMemberBus;
import com.apis.globedr.model.request.health.NewVisitDocRQ;
import com.apis.globedr.model.response.health.VisitDataRS;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.apis.globedr.model.step.org.AfterVisitMS;
import com.apis.globedr.model.step.orgMember.HealthDocMemberMS;
import com.apis.globedr.apis.CommunicationApi;
import com.apis.globedr.apis.HealthApi;
import com.apis.globedr.helper.Data;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class HealthDocumentStep extends Data {


    HealthApi healthApi = HealthApi.getInstant();

    CommunicationApi communicationApi = CommunicationApi.getInstant();
    OrgMemberBus memberBus = new OrgMemberBus();
    OrgBus orgBus = new OrgBus();
    AptBus aptBus = new AptBus();
    HealthBus healthBus = new HealthBus();



    @When("^As manager, I send health record to member$")
    public void userAddPatientDocs(List<HealthDocMemberMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setCreatedDate(Common.format(Common.today(), Text.FTIME_FULL));
            memberBus.orgAddHealthDoc(info);
        });
    }

    @When("^As user, I upload health document$")
    public void userUploadHealthDocs(List<HealthDocMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            healthBus.addHealthDocs(info);
        });
    }

    @When("^As user, I upload health document appointment$")
    public void userUploadHealthDocsAppt(List<HealthDocMS> list) {
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setApptSig(aptSig);
            healthBus.addHealthDocs(info);
        });
    }


    @And("I update health document that has type {string} and  description {string}")
    public void iUpdateHealthDocumentThatHasTypeAndDescription(String type, String desc, HealthDocMS newInfo) {
        HealthDocMS oldInfo = HealthDocMS.builder().description(desc).userSig(userSig).build().setMedicalType(type);
        String docSig = healthBus.getHealthDocSigs(oldInfo).get(0);
        newInfo.setUserSig(userSig);
        newInfo.setDocSig(docSig);
        healthBus.updateDescription(newInfo);
    }

    @And("I remove health document")
    public void iRemoveHealthDocument(List<HealthDocMS> list) {
        list.forEach(info ->{
            info.setUserSig(userSig);
            info.setDocSigs(healthBus.getHealthDocSigs(info));
            healthBus.removeHealthDocs(info);
        });
    }

    @When("^I move health document to \"([^\"]*)\"$")
    public void moveHealthDocs(String type,List<HealthDocMS> list) {
        list.forEach(info ->{
            info.setToMedicalType(type);
            info.setUserSig(userSig);
            info.setDocSigs(healthBus.getHealthDocSigs(info));
            healthBus.moveHealthDocs(info);
        });
    }


    @And("I load health document")
    public void iLoadHealthDocument(HealthDocMS info) {
        info.setUserSig(userSig);
        healthBus.loadHealthDocs(info);
    }


    @Given("^I load visit medical$")
    public void iLoadAllVisitMedicalHistory() {
        healthBus.loadVisitMedicalHistory(userSig);
    }

    @Given("^I load visit medical details$")
    public void iLoadAndGetVisitMedicalHistory() {
        String visitSig = healthBus.loadVisitMedicalHistory(userSig).get(0).getVisitSig();
        healthBus.loadVisitMedical(visitSig);
    }


    @Given("^Org send visit medical to below user$")
    public void orgSendVisitMedicalToBelowUser(Map<String, Object> dataTable) {
        communicationApi.dhyd2(apikey, dataTable);
    }


    @And("As org, I send After Visit to member name {string}")
    public void asOrgISendAfterVisitToMemberName(String name, AfterVisitMS info) {
        String userSig = memberBus.loadMember(name, orgSig).get(0).getUserSig();

        if (info.getDoctorName() != null)
            info.getVisitData().getVisitVital().addProvider(aptBus.getDoctor(orgSig, info.getDoctorName()));
        info.setOrgSig(orgSig);
        info.setUserSig(userSig);
        healthBus.newVisitData(info);
    }

    @And("As org, I update After Visit to member name {string}")
    public void asOrgIUpdateAfterVisitToMemberName(String name, AfterVisitMS info) {
        String userSig = memberBus.loadMember(name, orgSig).get(0).getUserSig();
        info.setVisitSig(healthBus.visitData(orgSig, userSig).get(0).getVisitSig());

        if (info.getDoctorName() != null)
            info.getVisitData().getVisitVital().addProvider(aptBus.getDoctor(orgSig, info.getDoctorName()));
        info.setOrgSig(orgSig);
        info.setUserSig(userSig);
        healthBus.updateVisitData(info);
    }

    @And("As org, I upload After Visit docs to member name {string}")
    public void asOrgIUploadAfterVisitDocsToMemberName(String name, List<NewVisitDocRQ> list) {
        String userSig = memberBus.loadMember(name, orgSig).get(0).getUserSig();
        list.forEach(info -> {
            info.setVisitSig(healthBus.visitData(orgSig, userSig).get(0).getVisitSig());
            info.setOrgSig(orgSig);
            info.setUserSig(userSig);
            healthBus.uploadDoc(info);
        });
    }

    @And("As manager, I load After Visit of member name {string}")
    public List<VisitDataRS> loadAfterVisit(String name) {
        String userSig = memberBus.loadMember(name, orgSig).get(0).getUserSig();
        return healthBus.visitData(orgSig, userSig);
    }


    @When("As manager, I load Visit Medical of member name {string}")
    public void loadVisitMedical(String name) {
        String userSig = memberBus.loadMember(name, orgSig).get(0).getUserSig();
        healthBus.loadVisitMedical(orgSig, userSig);
    }


}


