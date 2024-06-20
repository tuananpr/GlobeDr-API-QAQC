package com.apis.globedr.model.response.chat;

import com.apis.globedr.model.general.Actions;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversationRS {
    private String conversationSig;
    private Integer conversationId;
    private String name;
    private String avatarUrl;
    private String lastMsgText;
    private String lastMsgTime;
    private String lastSenderName;
    private String lastMsgLinkSig;
    private String lastMsgSig;
    private String firstDocUrl;
    private String channelPusher;
    private Integer lastMsgType;
    private Integer senderType;
    private Integer senderId;
    private Integer type;
    private Integer recipientCount;
    private Boolean isSendOnly;
    private Boolean isSeen;
    private Boolean enableVideoCall;
    private Boolean isOwner;
    private Actions actions;


}
