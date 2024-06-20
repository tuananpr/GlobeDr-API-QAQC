package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;

public class SubmitPostRQ {

    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    @JsonAlias({"postSig", "postSignature"})
    private String postSignature;

}
