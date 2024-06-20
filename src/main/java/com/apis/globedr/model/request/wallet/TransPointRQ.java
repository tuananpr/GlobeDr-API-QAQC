package com.apis.globedr.model.request.wallet;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class TransPointRQ {
    @JsonUnwrapped
    private FilterDate filterDate;
    private Integer walletStatus;

    Integer page;
}
