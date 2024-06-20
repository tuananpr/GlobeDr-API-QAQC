package com.apis.globedr.model.response.orgMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadGroupsRS {
    private Integer groupId;
    private Integer countMember;
    private String groupSig;
    private String name;
    private String avatarUrl;
    private boolean isDefault;
}
