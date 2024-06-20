package com.apis.globedr.model.request.subAccount;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountShareRQ {
    private String userSig;
    private String deviceId;
    private List<ShareInfoRQ> shareInfos;

}
