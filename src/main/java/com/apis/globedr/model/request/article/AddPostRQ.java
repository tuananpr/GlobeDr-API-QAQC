package com.apis.globedr.model.request.article;

import com.apis.globedr.model.general.Tag;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class AddPostRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSignature;
    @JsonAlias({"postSig", "postSignature"})
    private String postSignature;

    private String subject;
    private String categorySignature;
    private String msg;


    @JsonAlias({"postMsgSig", "postMsgSignature"})
    private String postMsgSignature;
    private Integer postStatus;
    private Integer type;
    private Integer appId;
    private List<Tag> inputTags;

}
