package com.apis.globedr.model.response.appointment;

import com.apis.globedr.model.request.appointment.PatientRQ;
import com.apis.globedr.model.step.account.AccountMS;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApptDetailsRS {
    private String apptSig;
    private String qrCode;
    private Patient patient;
    private String postSig;
    private String toSig;
    private String byUserSig;


}
