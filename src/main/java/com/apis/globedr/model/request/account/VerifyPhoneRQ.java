package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPhoneRQ {
    private String phone;
    private String accessKey;
    private String code;
}
