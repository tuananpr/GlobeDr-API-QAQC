package com.apis.globedr.model.step.subAccount;

import com.apis.globedr.enums.CarerType;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.request.subAccount.ShareInfoRQ;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubAccountMS {

    private String name;
    private String displayName;
    private String dob;
    private Integer carerType;
    private Integer sharedType;

    private Integer gender;
    private Double height;
    private Double weight;
    private String userSig;
    private String subSig;

    private boolean notLoadShared;
    @JsonUnwrapped
    Page page;

    private Country country;
    private District district;
    private Ward ward;
    private City city;

    private String deviceId;
    private ShareInfoRQ shareInfos;
    private String sharedConnectionName;

    public void setCarerType(Object carerType) {
        if(carerType != null){
            this.carerType = CarerType.value(carerType);
        }else{
            this.carerType = null;
        }
    }

    public void setSharedType(Object carerType) {
        if(carerType != null){
            this.sharedType = CarerType.value(carerType);
        }else{
            this.sharedType = null;
        }
    }
}
