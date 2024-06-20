package stepdefinition;

import com.apis.globedr.business.appointment.*;

import com.apis.globedr.business.consult.ConsultBus;
import com.apis.globedr.business.consult.ConsultUser;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.business.other.OtherBus;
import com.apis.globedr.enums.*;
import com.apis.globedr.model.request.appointment.*;
import com.apis.globedr.model.request.appointment.Weekday;
import com.apis.globedr.model.response.appointment.ListExamsSchedule;
import com.apis.globedr.model.response.appointment.TimeSheet;
import com.apis.globedr.model.step.account.AccountMS;
import com.apis.globedr.model.step.appointment.ApptMS;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.apis.globedr.model.request.appointment.ApptAssignDoctorRQ;
import com.apis.globedr.model.response.appointment.ApptDetailsRS;
import com.apis.globedr.model.request.appointment.DoctorScheduleVisitRQ;

import com.apis.globedr.model.response.appointment.Doctor;
import com.apis.globedr.apis.*;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.rest.core.response.Response;
import io.cucumber.java.en.Given;

import com.apis.globedr.helper.Data;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.testng.Assert;

import java.util.*;

public class AppointmentStep extends Data {


    AptApi aptApi = AptApi.getInstant();
    OtherBus otherBus = new OtherBus();
    AptBus aptBus = new AptBus();
    AptManageBus aptManageBus = new AptManageBus();
    HealthBus healthBus = new HealthBus();
    ConsultBus consultUser = new ConsultUser();


    @And("^I load doctor with specialty \"([^\"]*)\"$")
    public void iLoadDoctorWithSpecialty(String specialty) {
        ApptMS info = ApptMS.builder()
                .orgSig(orgSig)
                .specialtyCode(otherBus.loadSpecialties(specialty).get(0))
                .build();
        aptBus.loadListDoctor(info);
    }

    @And("^As user, I load appointments")
    public void userLoadAppointments(ApptMS info) {
        info.setUserMode(UserType.Patient);
        info.setPageDashboard(PageDashboard.PageProfile);
        aptBus.appointments(info);
    }

    @And("^As doctor, I load appointments")
    public void doctorLoadAppointments(ApptMS info) {
        info.setUserMode(UserType.Provider);
        info.setPageDashboard(PageDashboard.PageMedicalBussiness);
        aptBus.appointments(info);
    }


    @And("^As user, I load appointment details$")
    public void userLoadAppointmentDetails(ApptMS info) {
        info.setUserMode(UserType.Patient);
        info.setPageDashboard(PageDashboard.PageProfile);
        aptBus.info(info);
    }

    @And("^As doctor, I load appointment details$")
    public void doctorloadAppointmentDetails(ApptMS info) {
        info.setUserMode(UserType.Provider);
        info.setPageDashboard(PageDashboard.PageMedicalBussiness);
        aptBus.info(info);
    }

    @And("^As manager, I load appointments$")
    public void managerloadAppointments(ApptMS info) {
        info.setOrgSig(orgSig);
        aptManageBus.appts(info);
    }

    @And("^I make an appointment to org name is \"([^\"]*)\"$")
    public void iMakeAnAppointmentToOrg(String orgName, List<BookedApptOrg> bookedApts) {
        BookedApt bookedApt = bookedApts.get(0);
        bookedApt.setOrgName(orgName);
        bookedApt.setPatientSig(userSig);
        ApptDetailsRS rs = bookedApt.init();
        if (response.isSuccess()) aptSig = rs.getApptSig();

    }


    @And("^I make an appointment to org name is \"([^\"]*)\" and doctor name is \"([^\"]*)\"$")
    public void iMakeAnAppointmentToOrg(String orgName, String doctorName, List<BookedApptDoctor> bookedApts) {
        BookedApt bookedApt = bookedApts.get(0);
        bookedApt.setOrgName(orgName);
        bookedApt.setPatientSig(userSig);
        bookedApt.setDoctorName(doctorName);
        aptSig = bookedApt.init().getApptSig();
    }


    @And("^I make an invalid appointment to org name is \"([^\"]*)\"$")
    public void iMakeInvalidAnAppointmentToOrg(String orgName, List<BookedApptInvalid> bookedApts) {
        BookedApt bookedApt = bookedApts.get(0);
        bookedApt.setToSig(orgName);
        aptApi.appt(bookedApt);
    }


    @Given("^I load ui of appointment$")
    public void iLoadUiConfigOfAppointment(ApptMS info) {
        aptBus.uiConfig(info);
    }


    @And("^As doctor, I update appointment status that has patient name \"([^\"]*)\"$")
    public void doctorUpdateApptStatus(String patientName, List<ApptMS> list) {
        ApptMS apt = new ApptMS();
        apt.setUserMode(UserType.Provider);
        apt.setPageDashboard(PageDashboard.PageMedicalBussiness);
        apt.setPatientName(patientName);
        String aptSig = aptBus.info(apt).getApptSig();
        list.forEach(info -> {
            info.setApptSig(aptSig);
            aptManageBus.status(info);
        });
    }


    @And("^As manager, I update appointment status that has patient name \"([^\"]*)\"$")
    public void managerUpdateApptStatus(String patientName, List<ApptMS> list) {
        String aptSig = aptManageBus.getAptSig(orgSig, patientName);

        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setApptSig(aptSig);
            aptManageBus.status(info);
        });
    }


    private List<Weekday> initScheduleDefaultForDoctor(Object doctorName, String appointmentType) {
        Doctor doctor = aptBus.loadListDoctor(orgSig).getDoctorForSchedule(doctorName);

        List<Weekday> listDays = new ArrayList<>();
        // Adding  all schedule examination on current week for one doctor
        List<String> allDays = Common.getAllDaysOfCurrentWeekAsString(Text.FTIME_FULL);
        for (com.apis.globedr.enums.Weekday index : com.apis.globedr.enums.Weekday.values()) {
            OrgExaminationScheduleRQ body = new OrgExaminationScheduleRQ()
                    .setOrgSig(orgSig)
                    .setOnDate(allDays.get(index.value()))
                    .setAppointmentType(appointmentType);

            List<ExamsTime> list = aptBus.orgExaminationSchedule(body).getTimeExaminations();

            DoctorsOfWeekday doctorSchedule = DoctorsOfWeekday
                    .builder()
                    .displayName(doctor.getName())
                    .timeExaminations(list)
                    .userId(doctor.getUserId())
                    .userSig(doctor.getSignature())
                    .build();

            Weekday weekday = Weekday
                    .builder()
                    .weekday(index.value())
                    .doctorsOfWeekday(Arrays.asList(doctorSchedule))
                    .build();
            listDays.add(weekday);
        }
        return listDays;
    }


    @And("As manager, I create appointment schedule with type {string} for doctor {string}")
    public void onOrgICreateAppointmentScheduleWithType(String aptType, String doctorName, List<NewOrgDoctorScheduleRQ> list) {
        ExamsSchedule body = ExamsSchedule.initDefault()
                .setOrgSig(orgSig)
                .setOrgId(orgId)
                .setAppointmentType(AppointmentType.value(aptType));

        // Online Consultant : serviceSig , doctorSchedule
        aptManageBus.scheduleExamination(body);
        response.mustSucceed();

        NewOrgDoctorScheduleRQ newDoctorScheduleRQ = list.get(0)
                .setOrgSig(orgSig)
                .setWeekdays(initScheduleDefaultForDoctor(doctorName, aptType))
                .setServiceSig(aptBus.loadAptServices(orgSig).get(0).getProductSig());

        aptManageBus.newDoctorSchedule(newDoctorScheduleRQ).mustSucceed();

    }


    @Given("I load my work schedule")
    public void iLoadMyWorkSchedule() {
        aptManageBus.doctorScheduleVisitViews(userSig);
    }

    @Given("^I update my work schedule$")
    public void iUpdateMyWorkSchedule(List<TimeSheet> timeSheets) {
        DoctorScheduleVisitRQ info = DoctorScheduleVisitRQ.builder().userSig(userSig).doctorScheduleVisits(timeSheets).build();
        aptManageBus.doctorScheduleVisit(info);
    }

    @Given("^I clear my work schedule$")
    public void iClearMyWorkSchedule() {
        DoctorScheduleVisitRQ info = DoctorScheduleVisitRQ.builder().userSig(userSig).build();
        aptManageBus.doctorScheduleVisit(info);
    }

    @And("^As manager,  I update appointment that has patient name \"([^\"]*)\"$")
    public void onOrgIUpdateTheFirstAppointment(String patientName, ApptMS info) {
        ListExamsSchedule rsSchedule = aptManageBus.orgScheduleExaminations(orgSig);

        info.setOrgSig(orgSig);
        info.setApptSig(aptManageBus.getAptSig(orgSig, patientName));
        info.setDoctorSig(aptBus.loadListDoctor(info).getList().get(0).getSignature());
        info.setScheduleExaminationId(rsSchedule.getScheduleExaminationId(info.getShift(), Common.dayOfWeek(info.getOnDate())));
        info.setTimeType(info.getShift());
        aptBus.apptUpdate(info);
    }

    @When("I set configuration appointment")
    public void iSetConfigurationAppointment(ApptMS info) {
        info.setOrgSig(orgSig);
        aptManageBus.appointmentConfig(info);
    }

    @And("As manager, I load doctors to assign appointment")
    public void asManagerILoadAssignDoctor(List<DoctorsRQ> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            aptBus.doctors(info);
        });

    }

    @And("As manager, I choose a below appointment and assign to doctor {string}")
    public void asManagerIChooseAFirstAppoinemntAndAssignToDoctor(String doctorName, ApptMS info) {
        ApptMS doctors = ApptMS.builder().orgSig(orgSig).doctorName(doctorName).build();

        info.setOrgSig(orgSig);
        info.setApptSig(aptManageBus.getAptSig(info));
        info.setDoctorSig(aptBus.loadListDoctor(doctors).getList().get(0).getSignature());
        aptManageBus.apptAssignDoctor(info);
    }

    @And("As user, I choose a first appointment then comment {string} into consultation")
    public void commentConsultIntoApt(String comment, ApptMS info) {
        info.setUserMode(UserType.Patient);
        info.setPageDashboard(PageDashboard.PageProfile);
        consultUser.addComment(comment, aptBus.info(info).getPostSig());
    }

    @And("As doctor, I choose a first appointment then view comments into consultation")
    public void asDoctorIChooseAFirstAppointmentThenOpenConsultation(ApptMS info) {
        info.setUserMode(UserType.Provider);
        info.setPageDashboard(PageDashboard.PageMedicalBussiness);
        ConsultantApi.getInstant().getComments(aptBus.info(info).getPostSig());

    }

    @And("As doctor, I choose a first appointment then view actions into consultation")
    public void asDoctorIChooseAFirstAppointmentThenviewactionsConsultation(ApptMS info) {
        info.setUserMode(UserType.Provider);
        info.setPageDashboard(PageDashboard.PageMedicalBussiness);
        ConsultantApi.getInstant().getActions(aptBus.info(info).getPostSig());
    }

    @And("As user, I choose a first appointment then view actions into consultation")
    public void asUserIChooseAFirstAppointmentThenviewactionsConsultation(ApptMS info) {
        info.setUserMode(UserType.Patient);
        info.setPageDashboard(PageDashboard.PageProfile);
        ConsultantApi.getInstant().getActions(aptBus.info(info).getPostSig());
    }


    @And("I save appointment qrcode")
    public void iSaveAppointmentQrcode() {
        ApptMS info = new ApptMS();
        info.setUserMode(UserType.Patient);
        info.setPageDashboard(PageDashboard.PageProfile);
        Data.set(Text.QR_CODE, aptBus.info(info).getQrCode());
    }


    @And("As guest, I view appointment details by QRCode")
    public void asGuestIViewAppointmentDetailsByQRCode() {
        ApptMS body = ApptMS.builder()
                .qrCode(Data.get(Text.QR_CODE))
                .language(0)
                .build();
        aptBus.infoByQRcode(body);
    }

    @And("As doctor, I decline appointment")
    public void asDoctorIDeclineAppointment(List<ApptMS> list) {
        list.forEach(info -> {
            info.setUserMode(UserType.Provider);
            info.setDeviceId(AccountMS.getDefaultDeviceId());
            aptBus.doctorDecline(info);
        });

    }

    @And("I add insurance card {string} and identity card {string} for appointment")
    public void iAddInsuranceCardAndIdentityCardForAppointment(String idInsurance, String idCardNumber, List<HealthDocMS> list) {
        PatientRQ body = healthBus.adDocsToVerifyAptInfo(aptSig, userSig, list);
        body.setApptSig(aptSig);
        body.setIdInsurance(idInsurance);
        body.setIdCardNumber(idCardNumber);
        aptBus.patient(body);

        if (response.isSuccess()) {
            userSig = response.extract("data.appt.byUserSig");
        }
    }


    @And("I add invalid insurance, identity card for appointment")
    public void iAddInsuranceCardAndIdentityCardForAppointment() {
        PatientRQ body = PatientRQ.builder().build();
        body.setApptSig(aptSig);
        aptBus.patient(body);
    }

    private Boolean isHasPath(String jsonString, String path) {
        try {
            JsonPath.parse(jsonString).read(path);
        } catch (PathNotFoundException e) {
            return false;
        }
        return true;
    }

    @And("Appointment information into QRcode should be")
    public void appointmentInformationIntoQRcodeShouldBe(Map<String, Object> expectedTable) {
        String details = response.getBody().path("data.info.detail");
        for (Map.Entry<String, Object> field : expectedTable.entrySet()) {
            if (!field.getKey().isEmpty()) {
                Object expected = field.getValue();

                Assert.assertTrue(isHasPath(details, field.getKey()),
                        String.format("Try to get value into path '%s' but not found into \nReponse:\n%s", field.getKey(), details));
                Object actual = JsonPath.parse(details).read(field.getKey());

                // compare for Numeric
                if (expected != null && Common.isNumeric(expected.toString())) {
                    actual = Common.formatNumber(Double.parseDouble(actual.toString()));
                    expected = Common.formatNumber(Double.parseDouble(expected.toString()));
                }

                Assert.assertTrue(String.valueOf(actual).toLowerCase().contains(String.valueOf(expected).toLowerCase()),
                        String.format("Expected of key '%s' should contains '%s', but found '%s'\nReponse:\n%s", field.getKey(), expected, actual, response.asString()));
            }
        }
    }

    @Then("Book appointment UI should be")
    public void bookAppointmentUIShouldBe(List<String> fields) {
        List<Integer> expexted = ControlTypeAppt.getValueFromList(fields);

    }
}
