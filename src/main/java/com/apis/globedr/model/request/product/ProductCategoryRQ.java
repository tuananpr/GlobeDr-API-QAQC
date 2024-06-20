package com.apis.globedr.model.request.product;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductCategoryRQ {
    private Integer categoryId;
    private Integer status;
    private String categorySig;
    private String nameVI;
    private String nameEN;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

}
