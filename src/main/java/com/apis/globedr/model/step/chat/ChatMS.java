package com.apis.globedr.model.step.chat;

import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ChatMS {
    private String name;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String conversationSig;
    private String senderSig;
    private String msg;
    private String msgSig;
    private String conversationName;
    private String group;
    private String attachmentFileName;
    private String attachByMsgSig;
    private String createDate;

    @JsonAlias({"recipientNames", "recipientName"})
    private List<String> recipientName;
    private List<Recipient> recipients;
    private Integer ownerType;
    private String ownerSig;
    private Integer type;
    private Integer senderType;
    private Integer msgType;
    private Integer conversationType;
    private Boolean findExisted;
    private Boolean isOrg;
    private String viewerSig;
    private String deviceId;
    private String objectClick;
    private Integer viewerType;
    private Integer orderType;
    private Integer pageDashboard ;


    @JsonUnwrapped
    ImageFile file;

    public void setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
    }

    public void setRecipientName(Object info) {
        if (info instanceof String) {
            recipientName = Arrays.asList(((String) info).split(","));
        } else {
            this.recipientName = (List<String>) info;
        }
    }

    public void setOwnerType(Object info) {
        this.ownerType = SenderType.value(info);
    }

    public void setType(Object info) {
        this.type = ChatType.value(info);
    }

    public void setSenderType(Object info) {
        this.senderType = SenderType.value(info);
    }

    public void setMsgType(Object info) {
        this.msgType = ChatMsgType.value(info);
    }

    public void setViewerType(Object info) {
        this.viewerType = SenderType.value(info);
    }

    public void setOrderType(String type) {
        orderType = OrderType.value(type);
    }
    public void setPageDashboard(Object type) {
        pageDashboard = PageDashboard.value(type);
    }



}
