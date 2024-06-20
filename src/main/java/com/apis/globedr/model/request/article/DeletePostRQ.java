package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePostRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    @JsonAlias({"postSig", "postSignature"})
    private String postSignature;
    private String note;
    private String comment;
    private String categorySig;
    private Integer appId;
    private Boolean isSendNoti;

}
