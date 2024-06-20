package com.apis.globedr.model.request.user;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadJoinedOrgsRQ {
    private Integer language;
    @JsonUnwrapped
    Page page;
}
