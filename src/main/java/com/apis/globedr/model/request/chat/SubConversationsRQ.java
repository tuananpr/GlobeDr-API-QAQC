package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubConversationsRQ {
    private String orgSig;
    private List<String> conversationSigs = new ArrayList<>();
    private String customerCareSig;
    private boolean videoCall;
    @JsonUnwrapped
    Page page;
    @JsonUnwrapped
    FilterDate filterDate;
}
