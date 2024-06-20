package com.apis.globedr.model.request.article;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class PostsRQ {
    @JsonUnwrapped
    private Page page;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    private String postTitleMsg;
    @JsonAlias({"subject"})
    private String postTitle;
    private String fromDate;
    private String toDate;
    private Integer categoryStatus;
    private Integer categoryType;
    private Integer language;
    private Integer postSource;
    private Integer postType;
    private Integer postStatus;

    private List<Integer> listStatus;
    private List<Integer> listExcludeStatus;
    private Boolean isLoadMyPost;

}
