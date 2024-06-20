package com.apis.globedr.model.request.orgMember;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadMembersByGroupRQ {
    @JsonAlias({"name"})
    private String displayName;
    private String groupSig;
    private String orgSig;
    @JsonUnwrapped
    Page page;

}


