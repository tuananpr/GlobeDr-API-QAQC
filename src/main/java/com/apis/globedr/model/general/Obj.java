package com.apis.globedr.model.general;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Obj {
    @JsonAlias({"id", "Id"})
    private Integer id;
    @JsonAlias({"sig", "Sig"})
    private String sig;
}
