package com.apis.globedr.model.request.article;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ArticlePostsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySignature;
    @JsonAlias({"subject"})
    private String postTitle;
    private Integer language;
    @JsonUnwrapped
    Page page;


}
