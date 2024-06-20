package com.apis.globedr.model.response.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class StaffRS {
    private List<String> specialties;
    private String gdrLogin;
    @JsonAlias({"userSig", "userSignature"})
    private String userSignature;
    private String shareCode;
    private String insuranceCode;
    private String displayName;

    private List<String> orgs;
    private String publicName;
    private String title;
    private String profession;
    private String country;
    private String region;
    private String city;

    private Integer gender;
    private Integer userAttribute;
    private Integer userFeatureAttribute;
    private Integer userType;
    private Integer userStatus;

    private void setSpecialties(Object info){
        if(info instanceof String){
            specialties = Arrays.asList(((String) info).split(", "));
        }else{
            specialties = (List<String>) info;
        }
    };

}

