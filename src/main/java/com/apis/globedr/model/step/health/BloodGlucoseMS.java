package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.GlucoseMilestones;
import com.apis.globedr.enums.MeasurementUnit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodGlucoseMS {
    private Integer glucoseMilestone;
    private Object a1C;
    private Integer fromUnit;
    private Integer toUnit;
    private Object glucose;
    private String onDate;
    private String userSig;
    private String healthDataSig;

    public void setFromUnit(Object value) {
        this.fromUnit = MeasurementUnit.value(value);
    }
    public void setToUnit(Object value){
        this.toUnit = MeasurementUnit.value(value);
    }

    public void setGlucoseMilestone(Object value){
        this.glucoseMilestone = GlucoseMilestones.value(value);
    }
}
