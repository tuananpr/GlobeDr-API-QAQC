package com.apis.globedr.model.response.chat;

import com.apis.globedr.model.general.Doc;
import lombok.Data;

import java.util.List;

@Data
public class LoadMsgsRS {
    private List<LoadMsgsRS> attachments;
    private String conversationSig;
    private String msgSig;
    private String createdDate;
    private String senderSig;
    private String senderAvatarUrl;
    private String senderName;
    private List<Doc> Docs;
    private String msgText;

    private Integer msgId;
    private Integer parentMsgId;
    private Integer senderId;
    private Integer senderType;
    private Integer msgType;
    private Boolean isByMe;
    private Boolean reSend;


}
