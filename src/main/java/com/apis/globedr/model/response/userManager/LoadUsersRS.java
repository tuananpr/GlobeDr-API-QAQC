package com.apis.globedr.model.response.userManager;

import com.apis.globedr.model.general.Geo;
import com.apis.globedr.model.response.other.Specialty;
import lombok.Data;

import java.util.List;

@Data
public class LoadUsersRS {
    private String address;
    private String avatar;
    private String city;
    private String country;
    private String createdByOrg;
    private String displayName;
    private String gdrLogin;
    private String insuranceCode;
    private String phone;
    private String profession;
    private String publicName;
    private String region;
    private String shareCode;
    private String title;
    private String userSignature;
    private List<String> orgs;
    private Integer connectionCount;
    private Integer gender;
    private Integer userAttribute;
    private Integer userFeatureAttribute;
    private Integer userId;
    private Integer userStatus;
    private Integer userType;
    private Double kmDistance;
    private Double mileDistance;
    private boolean isVerified;
    private boolean isSystem;
    Geo geo;
    List<Specialty> specialties;
}
