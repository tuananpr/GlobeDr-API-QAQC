package com.apis.globedr.model.request.appointment;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsRQ {
    private String orgSig;
    @JsonAlias({"name", "doctorName"})
    private String name;
    private String specialtyCode;
    @JsonProperty("isLoadAssign")
    private Boolean isLoadAssign;
    private Boolean telemedicine;
    private Boolean vipDoctor;

    @JsonUnwrapped
    Page page;


}