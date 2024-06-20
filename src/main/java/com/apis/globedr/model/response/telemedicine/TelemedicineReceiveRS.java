package com.apis.globedr.model.response.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineReceiveRS{
    private String accessToken;
    private String roomSID;
    private String roomName;
    private Integer postId;
    private Integer videoCallStatus;
    private Integer maxTimeInSeconds;
    private Integer warningTimeInSeconds;
    private String postSig;
    private String deviceId;
    private Boolean limitedTime;
}
