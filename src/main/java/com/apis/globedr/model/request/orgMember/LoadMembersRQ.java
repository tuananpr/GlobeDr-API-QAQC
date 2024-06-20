package com.apis.globedr.model.request.orgMember;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadMembersRQ {
    private String orgSig;
    private String userSig;

    private Boolean isSharedHealthRecode;

    @JsonUnwrapped
    Page page;

    private String fromJoinDate;
    private String toJoinDate;
    private Integer statusMember;
    private Integer fromAge;
    private Integer toAge;
    @JsonAlias({"memberName"})
    private String displayName;
    private String gdrLogin;
    private String phone;
    private Integer gender;
    private Integer status;
    private Boolean isVerified;
    private String country;
    private String city;
    private String patientId;
    private Boolean isSharedHealthRecord;


}
