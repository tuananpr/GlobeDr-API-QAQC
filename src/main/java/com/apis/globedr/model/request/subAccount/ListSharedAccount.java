package com.apis.globedr.model.request.subAccount;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;


public class ListSharedAccount {
    private String userSig;
    private String name;
    private Integer carerType;
    @JsonUnwrapped
    Page page;

}