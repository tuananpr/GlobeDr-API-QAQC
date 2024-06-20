package com.apis.globedr.model.request.userManager;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserRQ {
    private List<String> userSigList;
    private Boolean isVerify;
}
