package com.apis.globedr.model.request.appointment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApptAssignDoctorRQ {
    private String orgSig;
    private String apptSig;
    private String doctorSig;

}
