package com.apis.globedr.model.request.health;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadBloodPressureRQ {
    private String userSig;
    private Integer toUnit;
    private String fromDate;

    @JsonUnwrapped
    Page page;

}


