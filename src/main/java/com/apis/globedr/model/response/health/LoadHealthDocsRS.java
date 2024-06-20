package com.apis.globedr.model.response.health;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadHealthDocsRS {

    private String docSig;
    private Integer attributes;
    private String description;
    private String docUrl;
    private String fileExt;
    private String text1;
    private String text2;
    private String text3;
    private Integer fileType;
    private Integer docType; // enums.docType
    private String createdDate;

}
