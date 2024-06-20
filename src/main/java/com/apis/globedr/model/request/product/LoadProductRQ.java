package com.apis.globedr.model.request.product;

import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadProductRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySig;
    private String toDate;
    private String description;
    private String name;
    private String fromDate;
    @JsonAlias({"orgProductType", "productServiceType"})
    private Integer productServiceType;
    @JsonProperty("isAdminLoad")
    private boolean isAdminLoad;
    @JsonProperty("isGlobedr")
    private boolean isGlobedr;
    @JsonUnwrapped
    Page page;


    public void setProductServiceType(Integer productServiceType) {
        this.productServiceType = ProductServiceType.value(productServiceType);
    }
}