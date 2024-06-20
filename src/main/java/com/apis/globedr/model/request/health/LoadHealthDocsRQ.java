package com.apis.globedr.model.request.health;

import com.apis.globedr.enums.MedicalType;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadHealthDocsRQ {
    private String userSig;
    private Integer medicalType;

    @JsonUnwrapped
    Page page;


    public void setMedicalType(String type){
        medicalType = MedicalType.value(type);
    }
}

