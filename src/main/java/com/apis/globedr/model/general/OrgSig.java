package com.apis.globedr.model.general;

import com.fasterxml.jackson.annotation.JsonAlias;


public class OrgSig {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

    public OrgSig(){}
    public OrgSig(String orgSig) {
        this.orgSig = orgSig;
    }
}
