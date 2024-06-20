package com.apis.globedr.model.request.medicalTest;

import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ProductServiceOrgsRQ {

    private Integer productServiceType;
    @JsonUnwrapped
    Page page;

    public void setProductServiceType(String type) {
        this.productServiceType = ProductServiceType.value(type);
    }
}
