package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadUsersToAddStaffsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonAlias({"displayName"})
    private String name;
    private String gdrLogin;
    private String phone;

    private String country;
    private String region;
    private String city;
    private String address;

    private Integer gender;
    @JsonUnwrapped
    private Page page;


}
