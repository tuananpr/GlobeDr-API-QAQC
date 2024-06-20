package com.apis.globedr.model.request.health;

import com.apis.globedr.enums.DocType;
import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewVisitDocRQ {

    private String orgSig;
    private String visitSig;
    private String userSig;
    private String note;
    private String description;
    private String createDate;
    private Integer docType;
    private Integer medicalType;
    private Integer attributes;

    @JsonUnwrapped
    ImageFile file;

    public NewVisitDocRQ setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }

    public void setDocType(Object value){
        docType = DocType.value(value);
    }

    public void setMedicalType(Object value){
        medicalType = MedicalType.value(value);
    }
}
