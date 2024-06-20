package com.apis.globedr.model.request.org;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class MoveStaffsRQ {
    private List<String> userSigs;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer toDeptId;
}
