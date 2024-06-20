package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class CoverRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;

    @JsonUnwrapped
    ImageFile file;
}
