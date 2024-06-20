package com.apis.globedr.model.request.article;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadCategoryWithPostRQ {
    private Integer categoryType;
    private Integer language;
    @JsonUnwrapped
    private Page page;
}
