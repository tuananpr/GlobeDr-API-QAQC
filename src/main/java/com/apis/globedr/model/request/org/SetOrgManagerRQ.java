package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetOrgManagerRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private List<String> userSigs;
    private Boolean isManager;
}
