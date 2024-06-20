package com.apis.globedr.model.general;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class DeviceId {
    private String deviceId;

    public DeviceId(String value) {
        this.deviceId = value;
    }
}
