package com.apis.globedr.model.response.org;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgsManageRS {
    private Integer orgId;
    private String orgSig;
    private String name;
    private String qrCode;
    private String qrCodeView;
}
