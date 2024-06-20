package com.apis.globedr.model.step.orgMember;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberMS {
    private String name;
    private String groupSig;
    private String orgSig;
    @JsonUnwrapped
    Page page;
}


