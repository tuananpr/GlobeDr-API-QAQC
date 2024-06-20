package com.apis.globedr.model.request.orgMember;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadGroupsRQ {
    @JsonAlias({"groupName"})
    private String name;
    private String orgSig;
    @JsonUnwrapped
    Page page;
}
