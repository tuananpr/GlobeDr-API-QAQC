package com.apis.globedr.model.general;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRCode {
    @JsonAlias({"code"})
    private String qrCode;

    public QRCode() {
    }

    public QRCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
