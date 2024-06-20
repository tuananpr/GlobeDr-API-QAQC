package com.apis.globedr.model.step.health;

import com.apis.globedr.enums.GrowthChartAge;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrowthChartMS {
    private Integer typeChartAge;
    private String userSIg;

    public void setTypeChartAge(Object value){
        this.typeChartAge = GrowthChartAge.value(value);
    }
}
