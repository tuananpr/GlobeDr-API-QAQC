package com.apis.globedr.model.response.chat;

import com.apis.globedr.model.general.Doc;
import lombok.Data;

import java.util.List;

@Data
public class SendMessageRS {
    private List<String> attachments;
    private String conversationSig;
    private String msgSig;
    private String msg;
    private String createdDate;
    private String senderSig;
    private String senderAvatarUrl;
    private Integer senderId;
    private Integer senderType;
    private String senderName;
    private List<Doc> docs;
    private String msgText;
    private Integer msgId;
    private Integer msgType;
    private Integer parentMsgId;
    private Boolean isByMe;
    private Boolean reSend;



}
