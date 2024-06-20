package com.apis.globedr.model.request.account;

import com.apis.globedr.enums.SourceNotiScreen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequireInfoRQ {
    private Integer screen;
    private String userSig;
    public void setScreen(Object info) {
        this.screen = SourceNotiScreen.value(info);
    }
}
