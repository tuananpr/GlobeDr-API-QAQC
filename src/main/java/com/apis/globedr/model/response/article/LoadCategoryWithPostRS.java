package com.apis.globedr.model.response.article;

import lombok.Data;

import java.util.List;
@Data
public class LoadCategoryWithPostRS {
    private Boolean isHotTitle;
    private String title;
    private String categorySignature;
    private Integer categoryId;
    List<LoadPostDetailRS> list;

}
