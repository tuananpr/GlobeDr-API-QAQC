package com.apis.globedr.model.request.noti;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDeviceRQ {
    private String osVersion;
    private String deviceId;
    private String handle;
    private String handlePushKit;
    private boolean isManage;
    private Integer installationId;
    private Integer platform;
    private String deviceModel;
    private String osName;

    public void setIsManage(boolean value){
        this.isManage = value;
    }


}

