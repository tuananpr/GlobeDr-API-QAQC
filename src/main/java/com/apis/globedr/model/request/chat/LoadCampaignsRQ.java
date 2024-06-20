package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class LoadCampaignsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private boolean orderClicked;
    private String name;
    private String fromDate;
    private String toDate;
    private List<String> ConversationSigs = new ArrayList<>();
    @JsonUnwrapped
    Page page;
}


