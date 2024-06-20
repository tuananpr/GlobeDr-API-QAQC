package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoadStaffsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer deptId;
    private String displayName;
    private Boolean isProvider;
    @JsonProperty("isAdminLoad")
    private Boolean isAdminLoad;
    private String specialtyCode;
    private String excludeSpecialtyCode;
    private Integer language;

    @JsonAlias({"isUserTrial", "isTrial"})
    private Boolean isTrial;
    @JsonUnwrapped
    Page page;
}


