package com.apis.globedr.model.request.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAssignTakenSampleRQ {
    private String deviceId;
    private String orderSig;
    private String orgSig;
    private String userSig;
}
