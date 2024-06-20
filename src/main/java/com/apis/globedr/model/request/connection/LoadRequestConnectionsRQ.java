package com.apis.globedr.model.request.connection;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadRequestConnectionsRQ {
    @JsonAlias({"sharedConnectionName"})
    private String name;
    private boolean notLoadShared;

    //@JsonAlias({"userSignature"})
    private String subSig;
    @JsonUnwrapped
    private Page page;
}
