package com.apis.globedr.model.request.org;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultConfigRQ {
    private String orgSig;
    private Integer maxTimeVideoCallInSeconds;
    private Integer warningTimeVideoCallInSeconds;
    private Integer limitedTimeVideoCallInSeconds;
    private Integer normalConsultantPntActFees;
    private Integer videoConsultantPntActFees;
}
