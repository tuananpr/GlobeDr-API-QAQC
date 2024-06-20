package com.apis.globedr.model.request.consult;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCommentRQ {
    private Boolean isPatient;
    private String temperature;
    private String userSig;
    private String postSig;
    private String msgSig;
    private String msg;
    private Double height;
    private Double weight;
}

