package com.apis.globedr.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRQ {
    private String userSig;
    private Integer bioLanguage;
    private Integer appLanguage;
}
