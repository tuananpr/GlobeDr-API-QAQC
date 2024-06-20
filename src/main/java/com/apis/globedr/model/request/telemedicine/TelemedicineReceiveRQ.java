package com.apis.globedr.model.request.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineReceiveRQ extends TeleMedicineInfo {
    private String userSigCaller;
    private Integer receiverType;
    private String channelName;
    private boolean anotherCall;
}
