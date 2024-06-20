package com.apis.globedr.model.request.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExamsDay {
    private Integer scheduleExaminationId;
    private String scheduleExaminationSig;
    private Integer weekday;
    private Integer fromMinute;
    private Integer toMinute;
    private Integer fromHour;
    private Integer toHour;
    @JsonProperty("isVisible")
    private boolean isVisible;



    public static ExamsDay initMorningTime(Integer weekday){
        return ExamsDay.builder()
                .fromHour(7)
                .toHour(11)
                .fromMinute(30)
                .toMinute(30)
                .weekday(weekday)
                .isVisible(true)
                .build();
    }

    public static ExamsDay initNoonTime(Integer weekday){
        return ExamsDay.builder()
                .fromHour(13)
                .toHour(17)
                .fromMinute(30)
                .toMinute(30)
                .weekday(weekday)
                .isVisible(true)
                .build();
    }

    public static ExamsDay initOvertime(Integer weekday){
        return ExamsDay.builder()
                .fromHour(18)
                .toHour(20)
                .fromMinute(0)
                .toMinute(0)
                .weekday(weekday)
                .isVisible(true)
                .build();
    }
}