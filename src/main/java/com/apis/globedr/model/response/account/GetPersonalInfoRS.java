package com.apis.globedr.model.response.account;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Geo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPersonalInfoRS {
    private String phoneSupport;
    private String avatar;
    private String gdrLogin;
    private String visitingCountry;
    private String publicName;
    private String displayName;
    private String dob;
    private String phone;
    private String workPhone;
    private String email;
    private String address;

    private String region;
    private String country;
    private String title;
    private String deviceId;
    private City city;
    @JsonUnwrapped
    Geo geo;
    private Boolean inviteCodeActivated;
    private Boolean requireUpdate;
    private Boolean telemedicine;
    private Integer language;
    private Integer measurementUnit;
    private Integer idCard;
    private Integer insuranceCode;
    private Integer gender;


}
