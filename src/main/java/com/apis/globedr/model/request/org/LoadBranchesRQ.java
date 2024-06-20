package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoadBranchesRQ {
    @JsonAlias({"orgSig", "orgSignature", "parentOrgSig"})
    private String orgSig;
    private Integer language;
    @JsonUnwrapped
    Page page;



}
