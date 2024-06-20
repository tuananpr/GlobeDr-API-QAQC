package com.apis.globedr.model.request.telemedicine;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelemedicineDoctorsRQ {
    private List<String> specialties;
    private boolean allowAppointment;
    private String deviceId;

}