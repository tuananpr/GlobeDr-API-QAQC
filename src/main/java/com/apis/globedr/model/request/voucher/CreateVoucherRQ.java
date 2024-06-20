package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.OrgSig;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class CreateVoucherRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySig;
    private String voucherSig;
    private String name;
    private String note;
    private String country;
    private String supportPhone;
    private String description;
    private String link;
    private String city;
    @JsonUnwrapped
    FilterDate date;
    private Integer weight;
    private Integer status;
    private Integer language;
    private Integer type;
    private boolean isOneUse;
    private boolean isShip;
    List<OrgSig> branchSigs;
}