package com.apis.globedr.model.request.connection;

import com.fasterxml.jackson.annotation.JsonAlias;

public class FollowOrgRQ {

    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Boolean isFollow;

}
