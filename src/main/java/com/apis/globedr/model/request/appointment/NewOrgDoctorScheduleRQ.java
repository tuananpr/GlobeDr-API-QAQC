package com.apis.globedr.model.request.appointment;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NewOrgDoctorScheduleRQ {
    private String specialtyCode;
    private String roomId;
    private String roomName;
    private String orgSig;
    private String serviceSig;
    private String fromDate;
    private String toDate;
    private List<Weekday> weekdays;
}
