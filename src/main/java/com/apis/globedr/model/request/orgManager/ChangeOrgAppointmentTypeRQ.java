package com.apis.globedr.model.request.orgManager;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ChangeOrgAppointmentTypeRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String appointmentType;
}
