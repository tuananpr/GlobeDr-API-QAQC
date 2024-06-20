package com.apis.globedr.model.request.health;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitDataRQ {
    private String orgSig;
    private String userSig;
}
