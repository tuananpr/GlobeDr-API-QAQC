package com.apis.globedr.model.response.chat;

import lombok.Data;

import java.util.List;

@Data
public class LoadCampaignsRS {
    List<RecipientRS> recipients;
    private List<LoadMsgsRS> attachments;
    private String conversationSig;
    private String msgSig;
    private String name;
    private String createdDate;
    private String msg;
    private String linkSig;
    private Integer conversationId;
    private Integer msgType;
    private Integer recipientCount;
    private Integer reciNo;
    private Integer viewNo;
    private Integer clickNo;
    private Integer reciPer;
    private Integer viewPer;
    private Integer clickPer;
    private Integer parentMsgId;
}
