package com.apis.globedr.model.request.connection;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class SearchUserRQ {
    private String name;
    @JsonUnwrapped
    private Page page;


}
