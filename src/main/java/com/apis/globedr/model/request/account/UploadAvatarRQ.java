package com.apis.globedr.model.request.account;

import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class UploadAvatarRQ {
    @JsonUnwrapped
    ImageFile file;

}
