package com.apis.globedr.model.request.orgMember;

import com.apis.globedr.model.general.file.ImageFile;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class OrgAddHealthDocRQ {

    private String orgSig;
    private String note;
    private String userSig;
    private String apptSig;
    private Integer medicalType;
    private Integer docType;
    private Integer attributes;
    private String description;
    private String createDate;

    @JsonUnwrapped
    ImageFile file;


}
