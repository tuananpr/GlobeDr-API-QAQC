package com.apis.globedr.model.response.orgMember;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberRS {
    private String authSig;
    private String userSig;
    private String joinDate;
    private String leaveDate;
    private String phone;
    private String displayName;
    private Integer gender;
    private Integer countSub;
    private String avatarUrl;
    private String country;
    private String city;
    private String dob;
    private String genderName;
    private String address;
    private String patientId;
    private Boolean isSharedHealthRecode;
    private Boolean isShareView;
    private Boolean isShareEdit;


}
