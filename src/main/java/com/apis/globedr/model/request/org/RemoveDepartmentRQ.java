package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RemoveDepartmentRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer deptId;
}
