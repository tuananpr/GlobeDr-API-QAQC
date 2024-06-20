package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmRecoveryPasswordRQ {
    private String code;
    private String userSignature;
    private Integer language;
}
