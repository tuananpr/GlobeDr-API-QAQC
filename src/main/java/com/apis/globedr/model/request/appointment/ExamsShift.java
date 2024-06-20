package com.apis.globedr.model.request.appointment;

import com.apis.globedr.enums.TimeType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExamsShift {
    private Integer timeType;
    private String timeTypeName;
    private ExamsDay t2;
    private ExamsDay t3;
    private ExamsDay t4;
    private ExamsDay t5;
    private ExamsDay t6;
    private ExamsDay t7;
    private ExamsDay cn;

    public static ExamsShift initMorningTime() {
        Integer time = TimeType.OnlyMorning.value();
        return ExamsShift.builder()
                .timeType(time)
                .t2(ExamsDay.initMorningTime(2))
                .t3(ExamsDay.initMorningTime(3))
                .t4(ExamsDay.initMorningTime(4))
                .t5(ExamsDay.initMorningTime(5))
                .t6(ExamsDay.initMorningTime(6))
                .t7(ExamsDay.initMorningTime(7))
                .cn(ExamsDay.initMorningTime(1))
                .build();
    }

    public static ExamsShift initNoonTime() {
        Integer time = TimeType.OnlyNoon.value();
        return ExamsShift.builder()
                .timeType(time)
                .t2(ExamsDay.initNoonTime(2))
                .t3(ExamsDay.initNoonTime(3))
                .t4(ExamsDay.initNoonTime(4))
                .t5(ExamsDay.initNoonTime(5))
                .t6(ExamsDay.initNoonTime(6))
                .t7(ExamsDay.initNoonTime(7))
                .cn(ExamsDay.initNoonTime(1))
                .build();
    }

    public static ExamsShift initOvertime() {
        Integer time = TimeType.Overtime.value();
        return ExamsShift.builder()
                .timeType(time)
                .t2(ExamsDay.initOvertime(2))
                .t3(ExamsDay.initOvertime(3))
                .t4(ExamsDay.initOvertime(4))
                .t5(ExamsDay.initOvertime(5))
                .t6(ExamsDay.initOvertime(6))
                .t7(ExamsDay.initOvertime(7))
                .cn(ExamsDay.initOvertime(1))
                .build();
    }
}
