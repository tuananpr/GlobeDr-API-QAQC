package com.apis.globedr.model.response.chat;

import com.apis.globedr.model.general.Actions;
import lombok.Data;

@Data
public class LoadConversationRS {

    private Integer conversationId;
    private String conversationSig;
    private String userSig;
    private String name;
    private String avatarUrl;
    private String lastMsgText;
    private String lastMsgTime;
    private String lastSenderName;

    private Integer lastMsgType;
    private Integer senderType;
    private Integer type;
    private Integer senderId;
    private Integer recipientCount;
    private Boolean isSendOnly;
    private Boolean enableVideoCall;
    private Boolean isOwner;
    private Boolean isOrg;
    private Actions actions;



}
