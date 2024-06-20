package com.apis.globedr.model.request.userManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUsageTimeRQ {
    private String tokenCaptcha;
    private String userSig;
    private Integer days;
}
