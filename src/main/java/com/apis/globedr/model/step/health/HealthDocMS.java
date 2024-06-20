package com.apis.globedr.model.step.health;


import com.apis.globedr.enums.DocType;
import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthDocMS {
    private String userSig;
    private String docSig;
    private String postSig;
    private String apptSig;
    private String createdDate;
    private List<String> docSigs;
    private String description;
    private Integer attributes;
    private Integer docType; // enums.docType
    private Integer medicalType; // enums.MedicalType
    private Integer toMedicalType; // enums.MedicalType

    @JsonUnwrapped
    Page page;

    @JsonUnwrapped
    private ImageFile file;


    public void setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
    }

    public HealthDocMS setDocType(Object value) {
        this.docType = DocType.value(value);
        return this;
    }

    public HealthDocMS setMedicalType(Object value) {
        this.medicalType = MedicalType.value(value);
        return this;
    }


    public HealthDocMS setToMedicalType(Object value) {
        this.toMedicalType = MedicalType.value(value);
        return this;
    }


}
