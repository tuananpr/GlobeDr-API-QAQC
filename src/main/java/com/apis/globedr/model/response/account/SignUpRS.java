package com.apis.globedr.model.response.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRS {
    private String userSignature;
    private String gdrLogin;
    private String displayName;
    private String title;
    private String phone;
    private String dob;
    private String avatar;
    private String country;
    private String visitingCountry;
    private String tokenType;
    private String accessToken;
    private String tokenExpires;
    private String refreshToken;
    private Boolean isOrgAdmin;
    private Boolean isVerified;

    private Integer userId;
    private Integer userType;
    private Integer gender;
    private Integer measurementUnit;
    private Integer language;
    private Integer userFeatureAttribute;

}
