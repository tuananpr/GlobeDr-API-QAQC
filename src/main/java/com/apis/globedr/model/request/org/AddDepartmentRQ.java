package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AddDepartmentRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String name;
}
