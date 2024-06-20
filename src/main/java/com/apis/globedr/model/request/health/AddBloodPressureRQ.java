package com.apis.globedr.model.request.health;

import com.apis.globedr.enums.GlucoseMilestones;
import com.apis.globedr.enums.MeasurementUnit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddBloodPressureRQ {
    private Integer fromUnit;
    private Object systolic;
    private Object diastolic;
    private Object pulse;
    private String healthDataSig;
    private String userSig;
    private String onDate;

}
