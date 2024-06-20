package com.apis.globedr.model.request.wallet;

import com.apis.globedr.enums.PointActivity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsePointNotiRQ {
    private String deviceId;
    private String productSig;
    private Integer type;

    public void setType(Object type) {
        this.type = PointActivity.value(type);
    }
}
