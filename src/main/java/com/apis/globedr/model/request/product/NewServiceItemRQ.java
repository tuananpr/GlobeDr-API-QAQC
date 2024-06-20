package com.apis.globedr.model.request.product;

import com.apis.globedr.enums.Currency;
import com.fasterxml.jackson.annotation.JsonAlias;

public class NewServiceItemRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String productSig;
    private String itemSig;
    private String name;
    private String description;
    private String currencyName;
    private Integer itemId;
    private Integer price;
    private Integer currency;
    private Integer feesDoctorConsult;
    private Integer feesGlobeDrConsult;
    private Integer feesGlobeDr;
    public void setCurrency(Object currency) {
        this.currency = Currency.value(currency);
    }
}


