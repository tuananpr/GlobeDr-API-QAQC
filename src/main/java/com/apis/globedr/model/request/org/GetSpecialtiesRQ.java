package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSpecialtiesRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private boolean loadAll;
}
