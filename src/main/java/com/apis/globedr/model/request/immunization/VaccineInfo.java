package com.apis.globedr.model.request.immunization;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaccineInfo {
    private String dose;
    private String medId;
    private String vacId;
}
