package com.apis.globedr.model.response.consult;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Provider {
    private String userSig;
    private String userAvatar;
    private String userName;
    private String userTitle;
    private Integer userModeInQuestion;


}



