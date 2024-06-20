package com.apis.globedr.model.response.appointment;

import com.apis.globedr.model.general.Status;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppointmentsRS {
    private String appointmentSig;
    private String userSig;
    private String doctorSig;
    private String doctorName;
    private String name;
    private String patientName;
    private String productServiceName;

    private Status status;

}
