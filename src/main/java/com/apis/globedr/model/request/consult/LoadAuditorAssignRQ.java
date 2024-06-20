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
public class LoadAuditorAssignRQ {
    private String postSig;
    @JsonAlias({"name", "auditorName"})
    private String name;
    @JsonUnwrapped
    Page page;
}
