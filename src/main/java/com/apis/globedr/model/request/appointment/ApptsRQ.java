package com.apis.globedr.model.request.appointment;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ApptsRQ {

    private String orgSig;
    private String patientName;
    private Integer status;
    private Integer type;
    private Boolean onDateDESC;

    private String contactPhone;
    private String qrCode;

    @JsonUnwrapped
    FilterDate filterDate;
}


