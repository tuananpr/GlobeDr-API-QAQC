package com.apis.globedr.model.request.product;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductRQ {
    private String categorySig;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String productSig;
    private String code;
    private String name;
    private String nameEn;
    private String description;
    private String link;
    private Integer fromPrice;
    private Integer toPrice;
    private Integer currency;
    private Integer linkId;
    @JsonAlias({"orgProductType", "productServiceType"})
    private Integer orgProductType;
    private Integer paymentTypes;
    private Integer monthsUsedApp;
    private Integer points;
    private Integer feesDoctorConsult;
    private Integer feesGlobeDrConsult;
    private Integer feesGlobeDr;
    private boolean isVisible;
    private boolean isPublic;
    private boolean vip;
    private boolean isTrial;
    private boolean isFree;
    private boolean isInternal;
    private boolean allowVideoCall;
}

