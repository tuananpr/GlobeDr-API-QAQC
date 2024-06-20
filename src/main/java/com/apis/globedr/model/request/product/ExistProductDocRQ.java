package com.apis.globedr.model.request.product;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ExistProductDocRQ {
    private String docSig;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

}
