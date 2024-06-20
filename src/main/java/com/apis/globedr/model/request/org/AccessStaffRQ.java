package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AccessStaffRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    private Integer appType;
    private String deviceId;

}
