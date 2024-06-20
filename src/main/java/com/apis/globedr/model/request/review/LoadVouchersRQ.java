package com.apis.globedr.model.request.review;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class LoadVouchersRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonUnwrapped
    Page page;
    private String city;
    @JsonAlias({"name", "voucherName"})
    private String voucherName;
    private String mostComment;
    private String country;
    private String mostReview;
    private String categorySig;
    private String searchText;
    private boolean isPopular;
    private boolean isHot;
    private boolean isNew;
    private Integer language;

}
