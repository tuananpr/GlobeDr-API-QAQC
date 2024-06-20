package com.apis.globedr.model.response.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class SignInRS extends AccountToken{
    private String userSignature;
    private String gdrLogin;
    private String displayName;
    private String title;
    private String phone;
    private String dob;
    private String avatar;
    private String country;
    private String visitingCountry;
    private Integer userId;
    private Integer userType;
    private Integer gender;
    private Integer measurementUnit;
    private Integer language;
    private Integer userFeatureAttribute;
    private Boolean isOrgAdmin;
    private Boolean isVerified;

}

