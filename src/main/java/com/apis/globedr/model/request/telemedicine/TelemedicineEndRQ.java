package com.apis.globedr.model.request.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineEndRQ{
    private String postSig;
    private Integer seconds;
    private String deviceId;
    private String roomName;
    private Integer videoCallType;
}
