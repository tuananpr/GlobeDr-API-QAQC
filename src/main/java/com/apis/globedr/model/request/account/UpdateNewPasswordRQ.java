package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateNewPasswordRQ {
    private String deviceId;
    private String accessKey;
    private String newPassword;
    private Integer language;

}
