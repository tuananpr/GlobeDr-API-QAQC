package com.apis.globedr.model.request.orgMember;

import com.apis.globedr.model.general.FilterDate;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticGrowthChartRQ {
    private String orgSig;
    private String country;
    @JsonUnwrapped
    FilterDate filterDate;
}
