package com.apis.globedr.model.request.health;

import com.apis.globedr.enums.DocType;
import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.helper.FileHelper;
import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class AddHealthDocsRQ {
    private String userSig;
    private String apptSig;
    private Integer attributes;
    private String description;

    private Integer docType; // enums.docType
    private Integer medicalType; // enums.MedicalType
    private String createdDate;

    @JsonUnwrapped
    private ImageFile file;


}
