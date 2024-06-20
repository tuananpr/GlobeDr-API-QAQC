package com.apis.globedr.model.request.subAccount;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SharedAccountRQ {
    @JsonAlias({"userSignature"})
    private String userSig;
    private String deviceId;
}
