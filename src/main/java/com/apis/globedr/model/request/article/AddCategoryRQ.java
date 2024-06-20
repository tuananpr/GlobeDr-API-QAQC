package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AddCategoryRQ {
    private Integer language;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    private String categorySignature;

    private String categoryName;
    private String description;
    private Integer type;
    private Integer status;
    private Integer weight;

}
