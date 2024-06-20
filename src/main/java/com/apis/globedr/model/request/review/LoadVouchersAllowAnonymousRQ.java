package com.apis.globedr.model.request.review;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadVouchersAllowAnonymousRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String country;
    private String city;
    private String categorySig;
    @JsonAlias({"name", "voucherName"})
    private String voucherName;
    private Boolean isHot;
    private Boolean isNew;
    private Boolean isPopular;
    private Boolean isAdminLoad;
    private Integer language;
    private Double longitude;
    private Double latitude;
    @JsonUnwrapped
    Page page;



}
