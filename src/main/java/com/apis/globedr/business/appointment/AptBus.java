package com.apis.globedr.business.appointment;

import com.apis.globedr.apis.AptApi;
import com.apis.globedr.apis.ProductServiceApi;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.request.appointment.*;
import com.apis.globedr.model.request.product.ProductServicesRQ;
import com.apis.globedr.model.response.appointment.*;
import com.apis.globedr.model.response.product.ProductServicesRS;
import com.apis.globedr.model.step.appointment.ApptMS;
import com.rest.core.response.Response;

import java.util.List;

public class AptBus {

    private AptApi apt = AptApi.getInstant();

    public List<ProductServicesRS> loadAptServices(String orgSig){
        ProductServicesRQ body = ProductServicesRQ.builder()
                .orgSig(orgSig)
                .isAdminLoad(true)
                .productServiceType(ProductServiceType.AppointmentOrg.value())
                .build();
        return ProductServiceApi.getInstant().loadProductServices(body);
    }

    public ListDoctor loadListDoctor(ApptMS info) {
        return apt.loadListDoctor(info);
    }

    public ListDoctor loadListDoctor(String orgSig) {
        return loadListDoctor(ApptMS.builder().orgSig(orgSig).build());
    }

    public Doctor getDoctor(String orgSig, String doctorName) {
        return loadListDoctor(orgSig).getList().stream()
                .filter(doctor -> doctor.getName().equalsIgnoreCase(doctorName))
                .findFirst().orElse(null);
    }

    public ListOrgExamsRS orgExaminationSchedule(OrgExaminationScheduleRQ body) {
        return AptApi.getInstant().orgExaminationSchedule(body);
    }

    public Response doctorDecline(ApptMS body){
        String apptSig = getAptSig(body);
        body.setApptSig(apptSig);
        return apt.doctorDecline(body);
    }

    public Response patient(PatientRQ body){
        return apt.patient(body);
    }

    public String getAptSig(ApptMS body){
        return apt.appointments(body).stream()
                .filter(body.getName() != null ? a -> a.getName().equalsIgnoreCase(body.getName()) : a -> true)
                .filter(body.getPatientName() != null ? a -> a.getPatientName().equalsIgnoreCase(body.getPatientName()) : a -> true)
                .map(a -> a.getAppointmentSig()).findFirst().orElse(null);
    }

    public List<AppointmentsRS> appointments(ApptMS body){
        return apt.appointments(body);
    }

    public ApptDetailsRS info(ApptMS body){
        body.setApptSig(getAptSig(body));
        return apt.info(body);
    }

    public Response apptUpdate(ApptMS body){
        return apt.apptUpdate(body);
    }
    public Response infoByQRcode(ApptMS body){
        return apt.infoByQRcode(body);
    }
    public Response uiConfig(ApptMS body){
        return apt.uiConfig(body);
    }
    public ListDoctor doctors(DoctorsRQ body){
        return apt.doctors(body);
    }


}
