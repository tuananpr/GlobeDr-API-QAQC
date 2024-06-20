package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class LoadMsgsRQ {

    //    private Integer viewerType;
//    private Integer afterMsgId;
//    private Integer beforeMsgId;
//    private String clientId;
    private String conversationSig;
    //    private String viewerSig;
    private Boolean isIncrement;
    @JsonUnwrapped
    Page page;

}
