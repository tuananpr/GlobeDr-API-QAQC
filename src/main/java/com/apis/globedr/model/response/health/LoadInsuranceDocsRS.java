package com.apis.globedr.model.response.health;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadInsuranceDocsRS {
    private String docTypeName;
    private Integer docType;
    private List<LoadHealthDocsRS> healthDocs;
}
