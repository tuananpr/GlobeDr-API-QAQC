package com.apis.globedr.model.response.health;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadDataBMIRS {
    private String sig;
    private String onDate;
    private Double weight;
    private Double height;
    private Double heightMinor;
    private Double head;
    private Double bmi;
    private Integer heightMajor;
}
