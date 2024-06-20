package com.apis.globedr.model.request.system;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.FilterDate;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgsGrowthChartRQ {
    private String country;
    private City city;
    @JsonUnwrapped
    FilterDate filterDate;
    private Integer type;
}
