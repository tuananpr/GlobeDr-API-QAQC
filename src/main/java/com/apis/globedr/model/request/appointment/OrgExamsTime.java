package com.apis.globedr.model.request.appointment;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class OrgExamsTime  {

    private String schedule;
    private String timeTypeName;
    private String sig;

    private Integer timeType;
    private Integer scheduleExaminationId;
    private Integer fromHour;
    private Integer fromMinute;
    private Integer id;
    private Integer toHour;
    private Integer toMinute;

    private boolean isWorked;

    public OrgExamsTime() { }

    public ExamsTime getExamsTime() {
        return ExamsTime.builder()
                .scheduleExaminationId(getId())
                .timeType(getTimeType())
                .build();
    }


}
