package com.apis.globedr.model.response.consult;

import com.apis.globedr.model.general.Doc;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Comment {
    private List<Doc> docs;
    private Integer msgId;
    private Integer userId;
    private Integer type;
    private String msgSig;
    private String msg;
    private String msgFormat;
    private String onDate;
    private String userSig;
    private String userAvatar;
    private String msgExtension;
    private String order;
    private String postSig;
    private String userName;
    private String userTitle;
    private boolean isRoot;
    private boolean isVisible;
    private boolean isHtml;
    private boolean unread;
}

