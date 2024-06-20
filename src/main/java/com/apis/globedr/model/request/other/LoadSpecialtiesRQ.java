package com.apis.globedr.model.request.other;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadSpecialtiesRQ {
    private String name;
    private String group;
    private Integer language;
    private Boolean includeSub;
    private Boolean onlySub;
    @JsonUnwrapped
    Page page;
}
