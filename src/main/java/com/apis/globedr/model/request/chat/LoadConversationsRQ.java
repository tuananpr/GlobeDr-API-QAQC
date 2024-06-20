package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoadConversationsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    @JsonUnwrapped
    Page page;
    private List<String> threadSigs = new ArrayList<>();
    private Integer type;
    private Integer pageDashboard;
    private Boolean isOrg; // true : load chat user-org, false : load chat user-user,


}
