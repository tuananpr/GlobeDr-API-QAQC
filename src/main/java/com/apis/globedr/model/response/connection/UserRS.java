package com.apis.globedr.model.response.connection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRS {

    private Integer userId;
    private String userSig;
    private String userName;
    private String userTitle;
    private String userAvatar;
    private Integer userType;

}
