package com.apis.globedr.model.request.consult;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewQuestionRQ {
    @JsonAlias({"postSignature", "postSig"})
    private String postSig;
    private Integer score;
    private String review;
}
