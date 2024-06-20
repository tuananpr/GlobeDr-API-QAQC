package com.apis.globedr.model.request.consult;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignQuestionRQ {
    @JsonAlias({"postSignature", "postSig"})
    private String postSig;
    @JsonAlias({"userSignature", "userSig"})
    private String userSig;
}
