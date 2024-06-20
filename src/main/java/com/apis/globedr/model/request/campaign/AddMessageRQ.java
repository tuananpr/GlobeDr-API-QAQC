package com.apis.globedr.model.request.campaign;

import com.apis.globedr.model.general.Recipient;

import java.util.List;


public class AddMessageRQ {
    private String msg;
    private String msgSig;
    private String senderSig;
    private String conversationName;
    private String group;
    private String attachmentFileName;
    private Integer msgType;
    private Integer senderType;
    private Integer conversationType;
    private Boolean findExisted;
    private List<Recipient> recipients;

}
