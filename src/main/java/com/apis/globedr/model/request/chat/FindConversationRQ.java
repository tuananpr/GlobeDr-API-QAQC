package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Recipient;
import lombok.Data;

import java.util.List;

@Data
public class FindConversationRQ {
    private Integer ownerType;
    private String ownerSig;
    private Integer type;
    private Boolean findExisted;
    private List<Recipient> recipients;
}
