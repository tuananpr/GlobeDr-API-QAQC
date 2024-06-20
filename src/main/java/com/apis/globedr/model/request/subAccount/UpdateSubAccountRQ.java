package com.apis.globedr.model.request.subAccount;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubAccountRQ extends SubAccountRQ{

    private Integer carerType;
    @JsonAlias({"userSig", "userSignature"})
    private String userSignature;


}
