package com.apis.globedr.model.request.article;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class LoadSystemPostRQ {
    @JsonUnwrapped
    Page page;
    private Integer status;
    private Integer language;
    private Integer type;
    private List<String> postSignatures;
    private String postTitleMsg;
    private String key;


}
