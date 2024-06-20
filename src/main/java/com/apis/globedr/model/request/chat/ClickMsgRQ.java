package com.apis.globedr.model.request.chat;

import lombok.Data;

@Data
public class ClickMsgRQ {
    private String objectClick;
    private String msgSig;
    private String conversationSig;

}
