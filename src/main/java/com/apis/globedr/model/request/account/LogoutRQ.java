package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRQ {
    private String deviceId;
}
