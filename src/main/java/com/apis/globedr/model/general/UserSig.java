package com.apis.globedr.model.general;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserSig {
    private String userSig;


    public UserSig(String userSig) {
        this.userSig = userSig;
    }
}
