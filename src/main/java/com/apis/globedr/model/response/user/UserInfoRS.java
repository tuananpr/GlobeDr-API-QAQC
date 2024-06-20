package com.apis.globedr.model.response.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRS {
    @JsonAlias({"patientId", "userId"})
    private Integer userId;
    private Integer userType;
    private Integer gender;
    private Integer connectionStatus;
    private String dob;
    @JsonAlias({"displayName", "patientName"})
    private String displayName;
    private String title;
    private String fullName;
    private String userAvatar;
    private String phone;
    private String workPhone;
    private String email;
    private String genderName;
    private String country;
    private String countryName;
    private String region;
    private String regionName;
    private String city;
    private String cityName;
    private String address;
    private String receivedConnectionSig;
    private String publicName;
    private String userSigTelemedicine;

    @JsonAlias({"userSig", "userSignature", "patientSig"})
    private String userSig;
    private String qrCode;

    private Boolean isMe;
    private Boolean telemedicine;
}
