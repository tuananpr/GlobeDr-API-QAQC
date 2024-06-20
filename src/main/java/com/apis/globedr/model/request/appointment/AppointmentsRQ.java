package com.apis.globedr.model.request.appointment;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class AppointmentsRQ {
    private Integer userMode;
    private Integer status;
    private Integer type;

    @JsonUnwrapped
    Page page;
}
