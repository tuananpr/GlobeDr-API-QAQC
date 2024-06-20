package com.apis.globedr.model.response.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountToken {
    private String accessToken;
    private String refreshToken;
    private String tokenExpires;
    private String tokenType;
}
