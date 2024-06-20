package com.apis.globedr.business.appointment;

import com.apis.globedr.enums.AppointmentType;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.response.appointment.ListOrgExamsRS;
import com.apis.globedr.model.request.appointment.OrgExaminationScheduleRQ;
import com.apis.globedr.model.request.product.ProductServicesRQ;
import com.apis.globedr.model.response.appointment.ApptDetailsRS;
import com.apis.globedr.apis.AptApi;
import com.apis.globedr.apis.ProductServiceApi;
import com.apis.globedr.apis.SearchApi;
import lombok.*;
import org.junit.Assert;

@Data
@NoArgsConstructor
public abstract class BookedApt {
    private String toSig; // orgSig
    private String orgName;
    private String productServiceSig; //productSig
    private String patientSig;
    private String doctorSig;
    private String doctorName;
    private String roomId;
    private Integer scheduleExaminationId;
    private String status;
    private Object appointmentType;
    private String onDate;
    private String specialtyCode;
    private String reasonExamination;
    private String notes;
    private String bloodVessel;
    private String bodyTemperature;
    private String systolic;
    private String diastolic;
    private String capillary;
    private Boolean immuGdr;
    private String addressExamination;
    private String homeMedicalServiceType;
    private String contactPhone;
    private String companyName;
    private String companyAddress;
    private String companyTax;
    private Integer timeType;
    private String reason;
    private Country country;
    private City city;
    private District district;
    private Ward ward;



    public void setAppointmentType(Object appointmentType) {

        this.appointmentType = AppointmentType.value(appointmentType);
    }

    private void validatePatient() {
        if (getPatientSig() == null) Assert.fail("Please set patientSig to book appointment");
    }

    private void validateOrgName() {
        if (getOrgName() == null) Assert.fail("Please set orgName to book appointment");
    }

    private void validateOrgSig() {
        if (getToSig() == null) Assert.fail("Please set orgName to book appointment");
    }

    private void validateAppointmentType() {
        if (getAppointmentType() == null) Assert.fail("Please set appointmentType to book appointment");
    }

    private void validateOnDate() {
        if (getOnDate() == null) Assert.fail("Please set onDate to book appointment");
    }


    protected void choosePatient() {
        validatePatient();
    }

    protected void chooseOrg() {
        validateOrgName();
        setToSig(SearchApi.getInstant().searchMedicalOrgs(getOrgName()).get(0).getSig());
    }

    protected void chooseFirstService() {
        validateOrgSig();
        ProductServicesRQ body = ProductServicesRQ.builder()
                .orgSig(getToSig())
                .isAdminLoad(true)
                .productServiceType(ProductServiceType.AppointmentOrg.value())
                .build();
        setProductServiceSig(
                ProductServiceApi.getInstant().loadProductServices(body)
                .get(0).getProductSig()
        );
    }


    protected abstract void chooseDoctorInRoom();

    protected void chooseTime() {
        validateAppointmentType();
        validateOrgSig();
        validateOnDate();

        OrgExaminationScheduleRQ body = new OrgExaminationScheduleRQ()
                .setOrgSig(getToSig())
                .setAppointmentType(getAppointmentType())
                .setOnDate(getOnDate());
        ListOrgExamsRS list = AptApi.getInstant().orgExaminationSchedule(body);

        setScheduleExaminationId(list.getList().stream()
                .filter(s -> s.getTimeTypeName().equalsIgnoreCase("Morning"))
                .map(s -> s.getId())
                .findFirst().orElse(null));
    }

    public ApptDetailsRS init() {
        prepare();
        return AptApi.getInstant().appt(this);
    }

    protected abstract void prepare();

}
