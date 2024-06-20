package com.apis.globedr.model.step.org;

import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class StaffMS {

    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

    private String deptName;
    private Integer toDeptId;
    private String toDeptName;
    private Integer deptId;

    @JsonAlias({"userSig", "userSignature"})
    private String userSig;
    private String displayName;
    private String country;
    private String email;
    private String title;
    private String password;
    private Integer featureAttributes; // orgStaffAttributes4AddStaff
    private Integer staffAttributes; // used to AddStaff
    private Boolean isTelemedicine;
    private Boolean telemedicine;
    private Boolean isAdmin;
    private Boolean isManager;
    private Boolean isProvider;

    @JsonAlias({"isUserTrial", "isTrial"})
    private Boolean isTrial;
    private Boolean vip;
    private List<String> userSigs;
    private Boolean isHide;
    private String specialtyCode;
    private String excludeSpecialtyCode;
    private Integer language;
    private Integer appType;
    private String deviceId ;

    @JsonProperty("isAdminLoad")
    private Boolean isAdminLoad;

    @JsonUnwrapped
    Page page;


    private void setAppType(Object info) {
        appType = AppType.value(info);
    }

    public StaffMS(String orgSig, String displayName, String country) {
        setOrgSig(orgSig);
        setCountry(country);
        setDisplayName(displayName);
    }


    public StaffMS setFeatureAttributes(Object info) {
        if(info instanceof String){
            this.featureAttributes = OrgFeatureAttributes.convert(Arrays.asList(((String) info).split(",")));
        }else{
            this.featureAttributes = OrgFeatureAttributes.value(info);
        }
        return this;
    }
    public StaffMS setStaffAttributes(Object info) {
        if(info instanceof String){
            this.staffAttributes = OrgStaffAttributes.convert(Arrays.asList(((String) info).split(",")));
        }else{
            this.staffAttributes = OrgStaffAttributes.value(info);
        }
        return this;
    }
}
