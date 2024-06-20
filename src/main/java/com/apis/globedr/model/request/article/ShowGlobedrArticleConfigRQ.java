package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowGlobedrArticleConfigRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Boolean showGlobedrArticle;
}
