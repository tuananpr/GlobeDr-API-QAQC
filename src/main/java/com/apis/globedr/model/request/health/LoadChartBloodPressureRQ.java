package com.apis.globedr.model.request.health;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadChartBloodPressureRQ {
    private String userSig;
    private Integer toUnit;
    private Integer typeChartAge;

    @JsonUnwrapped
    Page page;
}

