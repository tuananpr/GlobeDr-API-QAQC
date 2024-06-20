package com.apis.globedr.model.request.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRQ {
    private String conversationSig;
    private String msgSig;
    private String msg;
    private String createdDate;
    private String senderSig;
    private String clientId;
    private String attachByMsgSig;

    private Integer msgType;
    private Integer senderType;


}
