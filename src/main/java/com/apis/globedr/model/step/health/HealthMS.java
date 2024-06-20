package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.BloodType;
import com.apis.globedr.enums.HealthDataType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthMS {
    private String userSig;
    private Integer healthType;
    private Integer bloodType;

    public void setHealthType(Object value){
        this.healthType = HealthDataType.value(value);
    }

    public void setBloodType(Object value){
        this.bloodType = BloodType.value(value);
    }
}
