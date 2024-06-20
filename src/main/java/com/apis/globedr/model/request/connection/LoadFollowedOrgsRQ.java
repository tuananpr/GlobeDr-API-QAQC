package com.apis.globedr.model.request.connection;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadFollowedOrgsRQ {

    private String name;
    @JsonAlias({"userSig"})
    private String subSig;
    private Integer orgType;
    private Boolean allowRequestAppt;
    private Boolean getScore;
    private Boolean allowOrder;
    private Boolean notLoadShared;
    @JsonUnwrapped
    private Page page;
}

