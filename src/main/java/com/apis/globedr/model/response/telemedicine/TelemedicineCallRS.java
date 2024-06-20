package com.apis.globedr.model.response.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemedicineCallRS {
    private String chanelName;
    private Integer postId;
    private Integer userIdReceiver;
    private Integer maxTimeInSeconds;
    private Integer warningTimeInSeconds;
    private String postSig;
    private String userSigReceiver;
    private String deviceId;
    private Boolean limitedTime;
}

