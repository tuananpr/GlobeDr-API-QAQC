package com.apis.globedr.model.request.appointment;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
public class ExamsSchedule {
    private Integer orgId;
    private String orgSig;
    private Integer appointmentType;
    private String appointmentTypeName;
    private Integer serviceId;
    private String serviceSig;
    private ExamsShift morning;
    private ExamsShift noon;
    private ExamsShift overTime;

    public static ExamsSchedule initDefault(){
        return ExamsSchedule
                .builder()
                .morning(ExamsShift.initMorningTime())
                .noon(ExamsShift.initNoonTime())
                .overTime(ExamsShift.initOvertime())
                .build();
    }
}
