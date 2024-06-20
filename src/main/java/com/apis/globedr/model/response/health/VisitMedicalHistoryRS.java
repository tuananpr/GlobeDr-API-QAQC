package com.apis.globedr.model.response.health;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitMedicalHistoryRS {
    private Integer visitId;
    private String visitSig;
    private String name;
    private String sig;
}
