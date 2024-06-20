package com.apis.globedr.model.request.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weekday {
    private Integer weekday;
    private List<DoctorsOfWeekday> doctorsOfWeekday;
}
