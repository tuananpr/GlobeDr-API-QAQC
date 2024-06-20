package com.apis.globedr.model.request.order;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class OrderRQ {

    private Integer orderStatus;
    private Integer orderType;
    private Integer excludeOrderStatus;
    private Integer orderIds;

    private String displayName;
    private String deviceId;
    private String orgSig;
    private String userSig;
    private Boolean isDoctorIndicated;
    private Boolean isTakenSampleStaff;

    @JsonUnwrapped
    Page page;
    @JsonUnwrapped
    FilterDate filterDate;
}
