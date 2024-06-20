package com.apis.globedr.model.general;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VisitData {
    private VisitVital visitVital = new VisitVital();

    private String issuesObservationDescription;
    private String visitSubclinicalDescription;
    private String medicationDescription;
    private String instructionDescription;
    private String followUpDescription;

}

