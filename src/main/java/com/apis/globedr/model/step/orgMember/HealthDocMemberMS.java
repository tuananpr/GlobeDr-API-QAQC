package com.apis.globedr.model.step.orgMember;

import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;



@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HealthDocMemberMS {
    private String memberName;
    private String userSig;
    private String orgSig;
    private Integer attributes;
    private String description;
    private String note;
    private Integer medicalType;
    private Integer docType;
    private String createdDate;
    private String image;

    @JsonUnwrapped
    private ImageFile file;

    public void setFile(String pathFile){
        this.file = (ImageFile)FileFactory.getFile(pathFile);
    }

}
