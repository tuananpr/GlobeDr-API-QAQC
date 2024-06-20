package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DefaultCoverRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer imgId;
}
