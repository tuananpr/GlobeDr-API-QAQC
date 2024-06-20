package com.apis.globedr.model.request.consult;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadDoctorAssignRQ {
    private String postSig;
    @JsonAlias({"doctorName", "name"})
    private String name;
    @JsonUnwrapped
    Page page;
}
