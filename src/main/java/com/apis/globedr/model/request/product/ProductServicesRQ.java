package com.apis.globedr.model.request.product;

import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductServicesRQ {
    private String categorySig;
    private String fromDate;
    private String description;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String name;
    private String toDate;
    private Boolean isAdminLoad;
    @JsonAlias({"orgProductType", "productServiceType"})
    private Integer productServiceType;
    @JsonUnwrapped
    Page page;


    public void setProductServiceType(Integer productServiceType) {
        this.productServiceType = ProductServiceType.value(productServiceType);
    }
}
