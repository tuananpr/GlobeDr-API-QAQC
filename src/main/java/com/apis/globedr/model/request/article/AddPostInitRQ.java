package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPostInitRQ {
    private Integer language;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;

}
