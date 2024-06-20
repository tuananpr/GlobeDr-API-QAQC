package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;


public class UpdateInfoRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String intro;
}
