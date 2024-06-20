package com.apis.globedr.model.step.appointment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrgDoctorScheduleMS {
    private String specialtyCode;
    private String roomId;
    private String roomName;
    private List<Object> weekdays;
    private String orgSig;
    private String serviceSig;
    private String fromDate;
    private String toDate;


}
