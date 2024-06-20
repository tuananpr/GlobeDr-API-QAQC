package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Recipient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateConversationRQ {
    private String name;
    private Integer ownerType;
    private Integer type;
    private Boolean findExisted;
    private List<Recipient> recipients;


}
