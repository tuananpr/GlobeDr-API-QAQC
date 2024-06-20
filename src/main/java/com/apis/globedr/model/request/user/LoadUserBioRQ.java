package com.apis.globedr.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadUserBioRQ {
    private String userSig;
    private Integer language;
    private Integer forLanguage;
}
