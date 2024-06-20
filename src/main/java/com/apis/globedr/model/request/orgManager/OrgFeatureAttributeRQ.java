package com.apis.globedr.model.request.orgManager;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrgFeatureAttributeRQ {
    private List<String> orgSigList;
    private Integer OrgFeatureAttributes;
}
