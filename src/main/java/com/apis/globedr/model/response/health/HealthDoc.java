package com.apis.globedr.model.response.health;

import com.apis.globedr.enums.DocType;
import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.helper.FileHelper;
import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HealthDoc {
    private String docSig;
    private String orgSig;
    private Integer attributes;
    private String description;
    private String note;
    private String docUrl;
    private String fileExt;
    private String text1;
    private String text2;
    private String text3;
    private Integer fileType;
    private Integer docType; // enums.docType
    private Integer medicalType; // enums.MedicalType
    private String createdDate;
    private String userSig;
    private String apptSig;

    @JsonUnwrapped
    private File file;

    public void update(String filePath) {
        file = FileFactory.getFile(FileHelper.getFileFromResource(filePath).toPath());
    }


    public void setDocType(Object value){
        this.docType = DocType.value(value);
    }

    public void setMedicalType(Object value){
        this.medicalType = MedicalType.value(value);
    }
}
