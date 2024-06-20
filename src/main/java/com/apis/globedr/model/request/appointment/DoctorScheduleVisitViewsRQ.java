package com.apis.globedr.model.request.appointment;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorScheduleVisitViewsRQ {
    private String userSig;
    @JsonUnwrapped
    Page page;
}
