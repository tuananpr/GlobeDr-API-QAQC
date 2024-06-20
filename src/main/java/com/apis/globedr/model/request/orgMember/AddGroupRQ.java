package com.apis.globedr.model.request.orgMember;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AddGroupRQ {
    private String orgSig;
    private String groupSig;
    @JsonAlias({"groupName"})
    private String name;

}
