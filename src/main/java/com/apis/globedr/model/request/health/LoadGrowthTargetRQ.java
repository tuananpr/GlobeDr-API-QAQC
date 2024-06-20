package com.apis.globedr.model.request.health;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.HashMap;
import java.util.Map;

public class LoadGrowthTargetRQ {
    private String userSig;
    private Integer typeChartAge;
    private Integer toUnit;

    @JsonUnwrapped
    Page page;
}
