package com.apis.globedr.model.request.telemedicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeleMedicineInfo {
    private String postSig;
    private String orgSig;
    private Boolean webPlatform;
    private Integer videoCallType;
    private Integer chatType;
    private Integer userPlatform;
    private String deviceId;
}

