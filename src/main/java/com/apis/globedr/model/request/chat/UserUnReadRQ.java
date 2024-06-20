package com.apis.globedr.model.request.chat;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class UserUnReadRQ {
    private String orgSig;
    private String groupSig;
    private String msgSig;
    @JsonUnwrapped
    Page page;


}
