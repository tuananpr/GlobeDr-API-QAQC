package com.apis.globedr.model.request.chat;

import lombok.Data;

@Data
public class ConversationNameRQ {
    private String conversationName;
    private String conversationSig;
    private Integer viewerType;
}
