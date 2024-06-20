package com.apis.globedr.model.request.product;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ServiceItemsRQ {
    private String productSig;
    private String itemSig;
    private String name;
    private Integer language;
    @JsonUnwrapped
    Page page;
}