package com.apis.globedr.model.request.voucher;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CreateCategoryRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySig;
    private String nameVN;
    private String nameEN;
    private Integer weight;
}
