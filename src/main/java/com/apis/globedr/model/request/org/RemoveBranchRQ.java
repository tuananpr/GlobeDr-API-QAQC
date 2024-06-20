package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class RemoveBranchRQ {
    @JsonAlias({"parentOrgSig"})
    private String orgSig;
    @JsonAlias({"orgSig", "orgSignature"})
    private String branchSig;
}
