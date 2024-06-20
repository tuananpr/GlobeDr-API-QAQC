package com.apis.globedr.model.request.branch;

import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class UpdateBranchRQ {

    private String parentOrgSig;
    private String tokenCaptcha;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String orgName;

    private List<Phone> phones;
    private Integer type;
    private Integer status;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    private String region;

    private String zipCode;
    private String email;
    private String fax;
    private String website;
    private String workHours;

    private City city;
    private Country country;
    private District district;
    private Ward ward;
}
