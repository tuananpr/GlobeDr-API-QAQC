package com.apis.globedr.model.request.user;

import com.apis.globedr.enums.UserBioDataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserBioRQ {
    private String bioSig;
    private String fromYear;
    private String toYear;
    private String line1;
    private String line2;
    private String note;
    private String referenceURL;
    private String roPresent;
    private Integer bioId;
    private Integer forLanguage;
    private Integer type;
    private Boolean isVisible;

    public void setType(Object info) {
        this.type = UserBioDataType.value(info);
    }
}


