package com.apis.globedr.model.request.consult;

import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class AddFileRQ {
    @JsonUnwrapped
    ImageFile file;
}
