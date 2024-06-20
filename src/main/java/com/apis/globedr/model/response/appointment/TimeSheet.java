package com.apis.globedr.model.response.appointment;

import com.apis.globedr.enums.TimeType;
import com.apis.globedr.enums.Weekday;
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
public class TimeSheet {
    private Integer weekday;
    private Integer timeType;

    public void setTimeType(Object timeType) {
        this.timeType = TimeType.value(timeType);
    }

    public void setWeekday(Object weekday) {
        this.weekday = Weekday.value(weekday);
    }
}
