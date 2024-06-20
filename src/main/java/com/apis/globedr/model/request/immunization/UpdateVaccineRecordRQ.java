package com.apis.globedr.model.request.immunization;


import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVaccineRecordRQ {
    private String userSignature;
    private String notes;
    private String receiveDate;
    private String orgName;
    private String orgSignature;
    private String vacLot;
    private List<VaccineInfo> vacList;
}

