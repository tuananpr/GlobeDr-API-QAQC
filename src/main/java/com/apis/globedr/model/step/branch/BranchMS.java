package com.apis.globedr.model.step.branch;

import com.apis.globedr.model.step.org.OrgMS;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class BranchMS extends OrgMS {
    private String parentOrgSig;
    private String tokenCaptcha;
    private String orgName;
}
