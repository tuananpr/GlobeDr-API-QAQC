package com.apis.globedr.model.request.user;

import com.fasterxml.jackson.annotation.JsonAlias;

public class GetSpecialtiesRQ {
    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    private Integer language;
}
