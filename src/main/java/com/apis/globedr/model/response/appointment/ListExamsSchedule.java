package com.apis.globedr.model.response.appointment;

import com.apis.globedr.model.request.appointment.ExamsDay;
import com.apis.globedr.model.request.appointment.ExamsSchedule;
import com.apis.globedr.model.request.appointment.ExamsShift;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ListExamsSchedule {
    private List<ExamsSchedule> list;

    public List<ExamsDay> getExamsDays(String shiftName, String dayOfWeek) {
        List<ExamsShift> shifts = getExamsShift(shiftName);
        List<ExamsDay> result = shifts.stream()
                .map(s -> {
                    switch (dayOfWeek){
                        case "cn" : return s.getCn();
                        case "t2" : return s.getT2();
                        case "t3" : return s.getT3();
                        case "t4" : return s.getT4();
                        case "t5" : return s.getT5();
                        case "t6" : return s.getT6();
                        case "t7" : return s.getT7();
                        default: return null;
                    }
                })
                .collect(Collectors.toList());
        return result;
    }

    public List<ExamsShift> getExamsShift(String shiftName) {
        return getList().stream()
                .map(t -> {
                    switch (shiftName){
                        case "morning" : return t.getMorning();
                        case "noon" : return t.getNoon();
                        case "overTime" : return t.getOverTime();
                        default: return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public Integer getScheduleExaminationId(String shiftName, String dayOfWeek){
        return getExamsDays(shiftName, dayOfWeek).get(0).getScheduleExaminationId();
    }

}

