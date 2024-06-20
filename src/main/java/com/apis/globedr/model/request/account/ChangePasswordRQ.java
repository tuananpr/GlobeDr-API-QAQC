package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRQ {
    private String oldPassword;
    private String newPassword;
    private String deviceId;
}
