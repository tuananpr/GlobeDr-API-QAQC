package com.apis.globedr.model.request.user;

import com.apis.globedr.enums.UserDocType;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadCertificateRQ {

    private Integer userDocType;
    @JsonUnwrapped
    ImageFile file;


    public void setUserDocType(Object info) {
        this.userDocType = UserDocType.value(info);
    }

    public void setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
    }

}
