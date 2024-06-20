package com.apis.globedr.model.request.chat;

import lombok.Data;

@Data
public class LoadRecipientsRQ {
    private String conversationSig;
    private String name;
}
