package com.apis.globedr.model.response.appointment;

import com.apis.globedr.model.request.appointment.ExamsTime;
import com.apis.globedr.model.request.appointment.OrgExamsTime;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class ListOrgExamsRS {
    private List<OrgExamsTime> list;

    public List<ExamsTime> getTimeExaminations(){
        return getList()
                .stream().map(OrgExamsTime::getExamsTime)
                .collect(Collectors.toList());
    }
}
