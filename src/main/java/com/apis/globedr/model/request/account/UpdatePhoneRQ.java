package com.apis.globedr.model.request.account;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePhoneRQ {
    private String country;
    private String phone;
}
