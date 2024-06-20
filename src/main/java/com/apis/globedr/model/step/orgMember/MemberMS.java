package com.apis.globedr.model.step.orgMember;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberMS {
    private String groupName;
    private String groupSig;
    private String orgSig;
    private String qrCode;
    private String userSig;
    private String authSig;
    private String patientId;
    private Boolean isSharedHealthRecode;

    @JsonUnwrapped
    Page page;

    private String fromJoinDate;
    private String toJoinDate;
    private Integer statusMember;
    private Integer fromAge;
    private Integer toAge;
    private String displayName;
    private String gdrLogin;
    private String phone;
    private Integer gender;
    private Integer status;
    private Boolean isVerified;
    private String country;
    private String city;
    private Boolean isSharedHealthRecord;


}
