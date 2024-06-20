package com.apis.globedr.model.request.health;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBmiRQ {
    private Object heightMajor;
    private Object weight;
    private Object heightMinor;
    private Object head;
    private Integer fromUnit;
    private String onDate;
    private String userSig;
}
