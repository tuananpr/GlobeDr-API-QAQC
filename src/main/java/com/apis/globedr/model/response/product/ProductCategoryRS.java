package com.apis.globedr.model.response.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryRS {
    private Integer categoryId;
    private Integer status;
    private Integer countProducts;
    private String categorySig;
    private String nameVI;
    private String nameEN;
    private String orgSig;
}
