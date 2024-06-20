package com.apis.globedr.model.request.account;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRQ {

    private String country;
    private String password;
    private String tokenCaptcha;
    private String tokenCaptchaV3;
    private String gdrLogin;
    private String displayName;
    private String deviceId;
    private Integer userPlatform;
    private Integer language;


}
