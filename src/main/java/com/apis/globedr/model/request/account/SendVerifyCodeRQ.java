package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVerifyCodeRQ {
    private String userSignature;
    private Integer verifyType;
    private Integer language;

}