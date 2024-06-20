package com.apis.globedr.model.step.product;

import com.apis.globedr.enums.ProductCategoryStatus;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProductCategoryMS {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String nameVI;
    private String nameEN;
    private Integer status;
    private String categorySig;
    private String fromDate;
    private String toDate;
    private Integer language;


    @JsonUnwrapped
    Page page;

    public void setStatus(Object info) {
        this.status = ProductCategoryStatus.value(info);
    }
}
