package com.apis.globedr.model.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequireInfoRQ {
    private String userSig;
    private String dob;
    private String phone;
    private String countryCode;
    private Integer gender;
}
