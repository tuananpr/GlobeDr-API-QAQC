package com.apis.globedr.model.response.org;

import com.apis.globedr.model.general.Phone;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadOrgsRS extends OrgRS{

    private String address;
    private String subdomain;
    private Integer type;
    private Integer status;
    private Boolean isBranch;
    private String parentOrgId;


}
