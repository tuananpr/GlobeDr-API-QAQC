package com.apis.globedr.model.request.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurnOnCustomerCareRQ {
    private String orgSig;
    private Boolean isOnline;

    public void setIsOnline(boolean value){
        this.isOnline = value;
    }
}
