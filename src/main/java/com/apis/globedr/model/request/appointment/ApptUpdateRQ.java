package com.apis.globedr.model.request.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;


public class ApptUpdateRQ {
    private String orgSig;
    private String apptSig;
    private String doctorSig;
    private String deviceId;
    private String onDate;
    private Integer scheduleExaminationId;

    private Integer timeType;
}
