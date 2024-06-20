package com.apis.globedr.model.request.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeScanRQ {
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String deviceId;
    private String qrCode;
    private Integer pageDashboard;
}
