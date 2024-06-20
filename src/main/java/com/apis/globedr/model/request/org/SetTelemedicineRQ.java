package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SetTelemedicineRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private List<String> userSigs;
    @JsonAlias({"telemedicine", "isTelemedicine"})
    private Boolean telemedicine;
}