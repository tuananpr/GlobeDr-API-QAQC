package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoadPostDetailRQ {
    @JsonAlias({"postSig", "postSignature"})
    private String postSignature;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    private Integer appId;
    private String note;
    private String comment;
    private String categorySig;
    private Boolean isSendNoti;


}
