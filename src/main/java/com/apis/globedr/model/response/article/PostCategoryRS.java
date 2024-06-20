package com.apis.globedr.model.response.article;

import lombok.Getter;

@Getter
public class PostCategoryRS {
    private Integer categoryId;
    private String categorySignature;
    private String categoryName;
    private String description;
    private Integer weight;
    private Integer language;
    private Integer status;
    private Integer type;
    private Integer categoryStatus;
    private Integer categoryType;
    private String orgSignature;

}
