package com.apis.globedr.model.request.appointment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentConfigRQ {
    private String orgSig;
    private String visitedAppointmentNotiVi;
    private String visitedAppointmentNotiEN;
    private Boolean unAllowDoctorUpdateStatus;
    private Boolean isShowConfirmPatientInfo;
    private Integer attributes;

}
