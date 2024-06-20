package com.apis.globedr.model.response.branch;

import com.apis.globedr.model.response.org.OrgRS;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchRS extends OrgRS {
    private Boolean isBranch;
    private String parentOrgSig;

}
