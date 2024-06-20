package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;

public class NewDoctorRQ {
    private Integer deptId;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String email;
    private String title;
    private String displayName;
    private String password;
    private Integer staffAttributes; // orgStaffAttributes4AddStaff
    private Boolean isProvider;
    @JsonAlias({"telemedicine", "isTelemedicine"})
    private Boolean telemedicine;
    @JsonAlias({"vip", "isVip"})
    private Boolean vip;

}
