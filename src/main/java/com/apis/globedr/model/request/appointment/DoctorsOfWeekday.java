package com.apis.globedr.model.request.appointment;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorsOfWeekday {
    private String displayName;
    private String userSig;
    private Integer userId;
    private List<ExamsTime> timeExaminations;
}