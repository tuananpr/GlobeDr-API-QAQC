package com.apis.globedr.model.request.health;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadDataBmiRQ {
    private String userSig;
    private Integer toUnit;

    @JsonUnwrapped
    Page page;
}

