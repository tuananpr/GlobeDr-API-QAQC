package com.apis.globedr.model.request.review;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class GetReviewRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonUnwrapped
    Page page;
}
