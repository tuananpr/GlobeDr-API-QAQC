package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UITypeRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String orgUIType;


}
