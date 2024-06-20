package com.apis.globedr.model.request.article;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AddSystemPostRQ {
    private String subject;
    private String msg;
    private String postMsgSignature;
    @JsonAlias({"postSig", "postSignature"})
    private String postSignature;
    private String key;
    private String linkPostSig;
    private Integer postId;
    private Integer type;
    private Integer language;

}
