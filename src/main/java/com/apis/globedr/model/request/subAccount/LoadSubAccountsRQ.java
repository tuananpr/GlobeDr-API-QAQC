package com.apis.globedr.model.request.subAccount;

import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoadSubAccountsRQ {
    @JsonAlias({"userSig", "userSignature"})
    private String userSignature;
    private String name;
    private Integer carerType;
    @JsonUnwrapped
    Page page;


}
