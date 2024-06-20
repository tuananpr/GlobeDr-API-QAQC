package com.apis.globedr.model.step.immunization;

import com.apis.globedr.model.request.immunization.VaccineInfo;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImmunizationMS {
    private String userSignature;
    private List<String> vacIds;
    private List<String> medIds;

    private String notes;
    private String receiveDate;
    private String orgName;
    private String orgSignature;
    private String vacLot;
    private List<VaccineInfo> vacList;


}
