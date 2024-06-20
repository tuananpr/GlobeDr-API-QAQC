package com.apis.globedr.model.request.appointment;

import com.apis.globedr.enums.AppointmentType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrgExaminationScheduleRQ {
    private Integer appointmentType;
    private String onDate;
    private String orgSig;

    public OrgExaminationScheduleRQ setAppointmentType(Object appointmentType) {
        this.appointmentType = AppointmentType.value(appointmentType);
        return this;
    }
}
