package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.GrowthChartAge;
import com.apis.globedr.enums.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BmiMS {
    private String userSig;
    private String orgSig;
    private String healthDataSig;

    private Object heightMajor;
    private Object weight;
    private Object heightMinor;
    private Object head;
    private Integer fromUnit;
    private Integer toUnit;
    private Integer typeChartAge;
    private String onDate;


    public void setToUnit(Object value){
        this.toUnit = MeasurementUnit.value(value);
    }
    public void setFromUnit(Object value){
        this.fromUnit = MeasurementUnit.value(value);
    }

    public void setTypeChartAge(Object value){
        this.typeChartAge = GrowthChartAge.value(value);
    }
}
