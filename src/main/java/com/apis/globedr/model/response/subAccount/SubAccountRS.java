package com.apis.globedr.model.response.subAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubAccountRS {

    private String avatar;
    private String userSignature;
    private Boolean isMainAccount;
    private Integer userId;
    private String displayName;

}
