package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.MeasurementUnit;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrowthTargetMS {
    private String userSig;
    private Double heightBirth;
    private Double weightBirth;
    private Double heightFather;
    private Double heightMother;
    private Double heightTarget;
    private Double weightTarget;
    private String growthTargetAgeName;
    private Integer fromUnit;
    private Integer toUnit;

    public void setFromUnit(Object value) {
        this.fromUnit = MeasurementUnit.value(value);
    }

    public void setToUnit(Object value){
        this.toUnit = MeasurementUnit.value(value);
    }

    public void updateGrowthTarget(String growthTargetAgeName) {
        switch (growthTargetAgeName.toUpperCase()) {
            case "TO 4 MONTH":
                heightTarget = heightBirth + 14;
                weightTarget = weightBirth * 2;
                break;
            case "TO 1 YEAR":
                heightTarget = heightBirth + 25;
                weightTarget = weightBirth * 3;
                break;
            case "TO 6 YEAR":
                heightTarget = heightBirth + 66;
                weightTarget = weightBirth * 6;
                break;
            case "TO 12 YEAR":
                heightTarget = heightBirth + 100;
                weightTarget = weightBirth * 12;
                break;
        }
    }



}
