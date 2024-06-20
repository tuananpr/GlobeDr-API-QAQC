package com.apis.globedr.model.request.medicalTest;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class OrderRQ {
    private String userSig;
    private String orgSig;
    private String displayName;
    private String deviceId;
    private Integer orderIds;
    private Integer excludeOrderStatus;
    private Integer orderStatus;
    private Integer orderType;

    private boolean isDoctorIndicated;

    @JsonUnwrapped
    FilterDate filterDate;

    @JsonUnwrapped
    Page page;

}
