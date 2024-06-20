package com.apis.globedr.model.response.user;

import com.apis.globedr.model.response.org.OrgRS;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class LoadJoinedOrgsRS {
    @JsonUnwrapped
    OrgRS org;

}
