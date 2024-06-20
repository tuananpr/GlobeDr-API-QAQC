package com.apis.globedr.model.request.health;

import com.apis.globedr.enums.MeasurementUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMeasurementUnitRQ {
    private Integer unit;
    private String userSig;
    private void setUnit(Object value){
        this.unit = MeasurementUnit.value(value);
    }
}
