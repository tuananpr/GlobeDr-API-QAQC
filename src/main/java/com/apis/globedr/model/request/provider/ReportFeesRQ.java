package com.apis.globedr.model.request.provider;

import com.apis.globedr.enums.ReportFeesType;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportFeesRQ {
    private String orgSig;
    @JsonUnwrapped
    private FilterDate filterDate;

    private Integer reportFeesType;

    public void setReportFeesType(Object value) {
        this.reportFeesType = ReportFeesType.value(value);
    }
}

