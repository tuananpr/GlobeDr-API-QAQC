package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.GrowthChartAge;
import com.apis.globedr.enums.MeasurementUnit;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodPressureMS {
    private String healthDataSig;
    private String userSig;
    private String orgSig;

    private Integer fromUnit;
    private Integer typeChartAge;
    private Integer toUnit;
    private Object systolic;
    private Object diastolic;
    private Object pulse;
    private String onDate;

    private String fromDate;

    public void setToUnit(Object value){
        this.toUnit = MeasurementUnit.value(value);
    }

    public void setFromUnit(Object value) {
        this.fromUnit = MeasurementUnit.value(value);
    }
    public void setTypeChartAge(Object value){
        this.typeChartAge = GrowthChartAge.value(value);
    }
    @JsonUnwrapped
    Page page;

}

