package com.apis.globedr.model.general;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vital {
    private Integer height;
    private Integer weight;
    private Integer bloodPressure;
    private Integer pulse;
    private Integer respiration;
    private Integer physicalType;
    private Double bmi;
    private Double head;
}
