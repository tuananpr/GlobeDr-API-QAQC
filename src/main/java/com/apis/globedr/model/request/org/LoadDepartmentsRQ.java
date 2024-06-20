package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class LoadDepartmentsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    //private String deptName; not support
    private Integer language;

    @JsonUnwrapped
    Page page;

}
