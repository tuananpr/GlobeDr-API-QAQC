package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetProviderRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
}
