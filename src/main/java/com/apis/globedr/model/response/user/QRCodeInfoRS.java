package com.apis.globedr.model.response.user;

import com.apis.globedr.model.general.QRCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRCodeInfoRS extends QRCode {
    private Integer qrCodeType;
    private String sig;
    private String msg;
    private String detail;
}
