package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifySignUpRQ {
    private String deviceId;
    private String code;
    private String userSignature;
    private Integer language;
}
