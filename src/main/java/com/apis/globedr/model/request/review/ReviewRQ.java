package com.apis.globedr.model.request.review;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ReviewRQ {
    private Integer score;
    private String review;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

}
